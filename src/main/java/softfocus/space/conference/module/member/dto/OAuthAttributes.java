package softfocus.space.conference.module.member.dto;

import lombok.Builder;
import lombok.Getter;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.member.MemberRoleType;
import softfocus.space.conference.module.member.enumeration.ProviderType;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String name;
    private String email;
    private String provider;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String provider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.provider = provider;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if("naver".equals(registrationId)){
            return ofNaver("id", attributes);
        }
        if("kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        }

        return null;
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> response = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");
        System.out.println(response);
        System.out.println(profile);
        return OAuthAttributes.builder()
                .name((String)profile.get("nickname"))
                .email((String)response.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider(ProviderType.KAKAO.getKey())
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>)attributes.get("response");
        System.out.println(response);
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .provider(ProviderType.NAVER.getKey())
                .build();
    }


    public Member toEntity(){
        return Member.builder()
                .nickname(name)
                .email(email)
                .role(MemberRoleType.USER)
                .provider(provider)
                .build();
    }
}
