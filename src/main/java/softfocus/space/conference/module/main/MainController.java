package softfocus.space.conference.module.main;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.member.MemberOauthRepository;
import softfocus.space.conference.module.member.MemberRepository;
import softfocus.space.conference.module.member.dto.MemberDTO;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {


    @GetMapping("/loginPage")
    public String login(){
        return "/login/login";
    }

    @GetMapping("/main")
    public String main(Model model,Principal principal){
        System.out.println(principal.getName());

        return "/main/main";
    }

    @GetMapping("/rtc")
    public String rtc(){
        return "/main/webrtc";
    }
}
