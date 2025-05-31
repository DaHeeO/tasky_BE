package com.tasky.tasky.global.jwt;

import com.tasky.tasky.domain.user.dto.OAuthLoginResponse;
import com.tasky.tasky.domain.user.entity.Users;
import com.tasky.tasky.domain.user.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "Auth";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TYPE = "Bearer";
    private static final long OAUTH_ACCESS_TOKEN_EXPIRE_TIME = 60*60*60L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 14 * 24 * 60 * 60 * 1000L; // 2주
    private final String secret;
    private Key key;
    private final UserRepository userRepository;

    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                         UserRepository userRepository) {
        this.secret = secret;
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.userRepository = userRepository;
    }

    public OAuthLoginResponse createOAuthAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Users user = userRepository.findByProviderId(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User Not Found"));

        // AccessToken 생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("providerId", user.getProviderId())
                .claim("userId", user.getUsrId())
                .setExpiration(new Date(now + OAUTH_ACCESS_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // 생성된 토큰을 토큰 dto에 담아 반환
        return OAuthLoginResponse.builder()
                .accessToken(accessToken)
                .name(user.getName())
                .image(user.getProfileImage())
                .build();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT 유효성 검사 실패: {}", e.getMessage());
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();

        String email = claims.getSubject();

        // 권한이 없다면 빈 권한이라도 넣어야 함
        return new UsernamePasswordAuthenticationToken(email, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
