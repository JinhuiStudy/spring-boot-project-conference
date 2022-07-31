package softfocus.space.conference.module.member.auth;

import lombok.Builder;
import lombok.Getter;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.member.MemberOauth;
import softfocus.space.conference.module.member.MemberRoleType;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes; // OAuth2 반환하는 유저 정보 Map
    private String nameAttributeKey;
    private String nickname;
    private String email;
    private String provider;

    private MemberOauth memberOauth;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String nickname, String email, String provider) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.email = email;
        this.provider = provider;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){

        if("kakao".equals(registrationId)){
            return ofKakao("id", attributes);
        }

        return null;
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> response = (Map<String, Object>)attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) response.get("profile");

        var oAuthAttributes = OAuthAttributes.builder()
                .nickname((String) profile.get("nickname"))
                .email((String) response.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .provider(ProviderType.KAKAO.getKey())
                .build();

        oAuthAttributes.memberOauth = MemberOauth.builder()
                .oauthId(attributes.get("id").toString())
                .profileNicknameNeedsAgreement(response.get("profile_nickname_needs_agreement").toString())
                .hasEmail(response.get("has_email").toString())
                .emailNeedsAgreement(response.get("email_needs_agreement").toString())
                .isEmailValid(response.get("is_email_valid").toString())
                .isEmailVerified(response.get("is_email_verified").toString())
                .email(response.get("email").toString())
                .build();

        return oAuthAttributes;
    }


    public Member toEntity(){
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .role(MemberRoleType.USER)
                .provider(provider)
                .build();
    }
}
