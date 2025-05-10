package com.tasky.tasky.global;

import com.tasky.tasky.domain.users.OAuth.CustomOAuth2UserService;
import com.tasky.tasky.domain.users.OAuth.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**", "/public/**").permitAll()  // 누구나 접근 가능
                        .anyRequest().authenticated()  // 그 외 요청은 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService)) // 사용자 정보 서비스 설정
                        .successHandler(oAuth2SuccessHandler) // 성공 핸들러 설정
                );

        return http.build();
    }
}
