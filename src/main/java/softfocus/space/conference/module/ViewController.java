package softfocus.space.conference.module;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import softfocus.space.conference.module.member.MemberService;
import softfocus.space.conference.module.member.dto.MemberDTO;
import softfocus.space.conference.module.today.TodayService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final MemberService memberService;
    private final TodayService todayService;

    @GetMapping("/loginPage")
    public String login(){
        return "/login/login";
    }

    @GetMapping("/author")
    public String author(Model model,Principal principal){
        List<MemberDTO> members = memberService.findAll();
        model.addAttribute("members",members);
        return "/login/author";
    }

    @GetMapping("/main")
    public String main(Model model,Principal principal){
        MemberDTO member = memberService.getMember(principal.getName());
        model.addAttribute("member",member);
        return "/main/main :: main";
    }

    @GetMapping("/edit")
    public String edit(Model model, Principal principal){

        if (principal == null) {
            return "/login/login";
        }
        var member = memberService.getMemberEntity(principal.getName());
        if (member == null) {
            return "/login/login";
        }

        model.addAttribute("projectID", todayService.getToday(member));
        return "/edit/edit :: content";
    }

    @RequestMapping("/content")
    public String loadContent() {
        return "/base";
    }

    @RequestMapping("/content/content1")
    public String getContent1() {
        return "/content :: content1";
    }

    @RequestMapping("/content/content2")
    public String getContent2() {
        return "/content :: content2";
    }

}
