package com.tasky.tasky.domain.users.OAuth;

import com.tasky.tasky.domain.users.entity.Provider;
import com.tasky.tasky.domain.users.entity.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Getter
@Slf4j
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String uid;
    private String email;
    private String username;
    private String profileImage;
    private Provider provider;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String uid, String email, String username, String profileImage, Provider provider) {
        this.attributes = attributes;
        this.nameAttributeKey=nameAttributeKey;
        this.uid = uid;
        this.email = email;
        this.username = username;
        this.profileImage = profileImage;
        this.provider = provider;
    }


    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        // 구글 외 다른 로그인 제공자도 고려할 경우 registrationId 사용
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String nameAttributeKey, Map<String, Object> attributes) {
        log.info("google 로그인 시도");
        return OAuthAttributes.builder()
                .uid((String) attributes.get("sub"))
                .email((String) attributes.get("email"))
                .username((String) attributes.get("name"))
                .profileImage((String) attributes.get("picture"))
                .provider(Provider.GOOGLE)
                .attributes(attributes)
                .nameAttributeKey(nameAttributeKey)
                .build();
    }

    public Users toEntity(Provider provider) {
        return Users.builder()
                .uid(uid)
                .provider(provider)
                .email(email)
                .username(username)
                .profileImage(profileImage) // ✅ 빠지면 안됨
                .build();
    }
}
