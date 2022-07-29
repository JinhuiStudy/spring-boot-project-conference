package softfocus.space.conference.module.main;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/video")
    public String video(){
        return "/main/video";
    }

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
