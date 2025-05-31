package com.tasky.tasky.domain.user.OAuth;

import com.tasky.tasky.domain.user.dto.OAuthLoginResponse;
import com.tasky.tasky.global.jwt.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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

        OAuthLoginResponse loginResponse = jwtTokenProvider.createOAuthAccessToken(authentication);


        String uri = String.format(
                "http://localhost:3000/oauth2/redirect?token=%s&name=%s&image=%s",
                loginResponse.getAccessToken(),
                java.net.URLEncoder.encode(loginResponse.getName(), "UTF-8"),
                java.net.URLEncoder.encode(loginResponse.getImage(), "UTF-8")
        );
        // 로그인 성공 시
        redirectStrategy.sendRedirect(request, response, uri);
    }
}
