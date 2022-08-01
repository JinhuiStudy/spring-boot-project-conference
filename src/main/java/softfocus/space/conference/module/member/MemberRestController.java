package softfocus.space.conference.module.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softfocus.space.conference.module.member.dto.GpsDTO;

import java.security.Principal;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberRestController {

    private final MemberService memberService;

    @PostMapping("/gps")
    public void saveGps(@RequestBody GpsDTO gpsDTO, Principal principal){
        memberService.saveGps(gpsDTO,principal.getName());
    }
}
