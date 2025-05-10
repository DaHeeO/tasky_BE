package com.tasky.tasky.global;

import com.tasky.tasky.domain.user.OAuth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // csrf 비활성화
                .formLogin(form -> form.disable())  // Form login 방식 비활성화
                .httpBasic(basic -> basic.disable())  // HTTP Basic 인증 방식 비활성화
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService)) // 사용자 정보 서비스 설정
//                        .successHandler(oAuth2SuccessHandler) // 성공 핸들러 설정
                )
                .authorizeHttpRequests(auth -> auth  // 경로 별 인가 작업
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
