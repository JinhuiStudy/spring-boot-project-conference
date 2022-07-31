package softfocus.space.conference.module.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import softfocus.space.conference.module.member.Member;

@Data
@AllArgsConstructor
public class MemberDTO {
    private Long idx;
    private String nickname;
    private String email;

    private Double lat;
    private Double lon;

    public MemberDTO(Member member){
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.lat = member.getLat();
        this.lon = member.getLon();
    }
}
