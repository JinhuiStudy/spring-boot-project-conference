package softfocus.space.conference.module.member.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.member.MemberOauthRepository;
import softfocus.space.conference.module.member.MemberRepository;
import softfocus.space.conference.module.member.dto.MemberDTO;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OAuthMemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    private final MemberOauthRepository memberOauthRepository;

    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        var oAuth2User = delegate.loadUser(userRequest);

        var registrationId = userRequest.getClientRegistration().getRegistrationId();
        var userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        var attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        var member = saveOrUpdate(attributes);

        httpSession.setAttribute("member", new MemberDTO(member));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private Member saveOrUpdate(OAuthAttributes attributes){
        var member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getNickname()))
                .orElse(attributes.toEntity());

        var optional = memberOauthRepository.findByOauthId(attributes.getMemberOauth().getOauthId());
        if(optional.isEmpty()){
            var memberOauth = memberOauthRepository.save(attributes.getMemberOauth());
            member.setMemberOauth(memberOauth);
        }
        return memberRepository.save(member);
    }
}
