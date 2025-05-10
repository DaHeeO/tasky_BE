package com.tasky.tasky.domain.users.OAuth;

import com.tasky.tasky.global.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        log.info("social login success");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 이메일 또는 사용자 고유 ID 추출 (구글 로그인 기준)
        String email = oAuth2User.getAttribute("email");  // 또는 sub, id 등 제공자에 따라 다름
        if (email == null) {
            throw new IllegalArgumentException("OAuth2User에서 email을 찾을 수 없습니다.");
        }

        // JWT 생성
        String accessToken = jwtTokenProvider.createToken(email);
//        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        String uri = "http://localhost:3000/?token="+accessToken;
        redirectStrategy.sendRedirect(request, response, uri);

    }
}
