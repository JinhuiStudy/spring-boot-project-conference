package softfocus.space.conference.module.member.dto;

import lombok.Data;
import softfocus.space.conference.module.member.Member;

@Data
public class MemberDTO {
    private String nickname;
    private String email;

    public MemberDTO(Member member){
        this.nickname = member.getNickname();
        this.email = member.getEmail();
    }
}
