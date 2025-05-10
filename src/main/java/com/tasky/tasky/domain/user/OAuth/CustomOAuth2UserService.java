package com.tasky.tasky.domain.user.OAuth;

import com.tasky.tasky.domain.user.entity.Users;
import com.tasky.tasky.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // Google은 항상 sub라는 고유 ID를 제공하므로 여기서 직접 가져오기
        String userNameAttributeName = "sub";

        Map<String, Object> attributesMap = oAuth2User.getAttributes();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, attributesMap);
        Users user = save(attributes);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                userNameAttributeName // 여기서 그냥 "sub"으로 지정
        );
    }

    private Users save(OAuthAttributes attributes) {
        Users user = userRepository.findByProviderIdAndProvider(attributes.getProviderId(), attributes.getProvider())
                // 우리 프로젝트에서는 유저의 닉네임/사진에 대한 실시간 정보가 필요 없기 때문에 update는 하지 않는다.
                .orElse(attributes.toEntity(attributes.getProvider()));
        return userRepository.save(user);
    }
}