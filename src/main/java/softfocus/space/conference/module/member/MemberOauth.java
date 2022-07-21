package softfocus.space.conference.module.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import softfocus.space.conference.module.common.BaseTime;

import javax.persistence.*;

@Entity @Table(name = "MEMBER_OAUTH")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MemberOauth extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // TODO Oauth 정보 다저장

    private String provider;
}
