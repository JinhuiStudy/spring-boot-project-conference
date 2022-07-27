package softfocus.space.conference.module.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping("/loginPage")
    public String login(){
        return "/index";
    }

    @GetMapping("/main")
    public String main(){
        return "/main/main";
    }

    @GetMapping("/rtc")
    public String rtc(){
        return "/main/webrtc";
    }
}
