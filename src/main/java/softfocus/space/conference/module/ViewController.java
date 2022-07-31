package softfocus.space.conference.module;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softfocus.space.conference.module.member.MemberService;
import softfocus.space.conference.module.member.dto.MemberDTO;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final MemberService memberService;

    @GetMapping("/loginPage")
    public String login(){
        return "/login/login";
    }

    @GetMapping("/main")
    public String main(Model model,Principal principal){
        System.out.println(principal.getName());
        MemberDTO member = memberService.getMember(principal.getName());
        model.addAttribute("member",member);
        return "/main/main";
    }

    @GetMapping("/edit")
    public String edit(){
        return "/edit/edit";
    }
}
