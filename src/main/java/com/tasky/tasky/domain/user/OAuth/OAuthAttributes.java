package com.tasky.tasky.domain.user.OAuth;

import com.tasky.tasky.domain.user.entity.Provider;
import com.tasky.tasky.domain.user.entity.Role;
import com.tasky.tasky.domain.user.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Slf4j
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String providerId;
    private String email;
    private String name;
    private String profileImage;
    private Provider provider;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String providerId, String email, String name, String profileImage, Provider provider) {
        this.attributes = attributes;
        this.nameAttributeKey=nameAttributeKey;
        this.providerId = providerId;
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.provider = provider;
    }


    // 어떤 경로로 들어왔는지 대해 분기처리 (받아오는 Data form 이 다르기 때문에 객체 생성을 달리 해줘야함. 근데 현재는 구글만
    public static OAuthAttributes of(String registrationId, String nameAttributeKey, Map<String, Object> attributes) {
        return ofGoogle(nameAttributeKey, attributes);
    }

    private static OAuthAttributes ofGoogle(String nameAttributeKey, Map<String, Object> attributes) {
        log.info("google 로그인 시도");
        return OAuthAttributes.builder()
                .providerId((String) attributes.get("sub"))
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .profileImage((String) attributes.get("picture"))
                .provider(Provider.GOOGLE)
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    public Users toEntity(Provider provider) {
        return Users.builder()
                .providerId(providerId)
                .provider(provider)
                .email(email)
                .name(name)
                .profileImage(profileImage)
                .role(Role.ROLE_USER)
                .build();
    }
}
