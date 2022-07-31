package softfocus.space.conference.module.member.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.member.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    public LoginSuccessHandler(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        var id = String.valueOf(oAuth2User.getAttributes().get("id"));
        var optional = memberRepository.findByMemberOauth_OauthId(id);

        if(optional.isEmpty()) {
            response.sendRedirect("/loginPage");
        }else{
            response.sendRedirect("/main");
        }
    }

}
