package softfocus.space.conference.module.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import softfocus.space.conference.module.common.BaseTime;

import javax.persistence.*;

@Entity @Table(name = "MEMBERS")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("이름")
    @Column(length = 10, nullable = false)
    private String nickname;

    @Comment("이메일")
    @Column(length = 30)
    private String email;

    @OneToOne
    @JoinColumn(name = "oauth_idx", referencedColumnName = "idx")
    private MemberOauth memberOauth;

    @Enumerated(EnumType.STRING)
    private MemberRoleType role;

    @Comment("위도")
    private Double lat;

    @Comment("경도")
    private Double lon;


}
