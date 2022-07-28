package softfocus.space.conference.module.member;

import lombok.*;
import org.hibernate.annotations.Comment;
import softfocus.space.conference.module.common.BaseTime;

import javax.persistence.*;

@Entity @Table(name = "MEMBER_OAUTH")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class MemberOauth extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // TODO Oauth 정보 다저장

    private String oauthId;
    private String profileNicknameNeedsAgreement;
    private String hasEmail;
    private String emailNeedsAgreement;
    private String isEmailValid;
    private String isEmailVerified;
    private String email;


}
