package softfocus.space.conference.module.member;

import lombok.*;
import org.hibernate.annotations.Comment;
import softfocus.space.conference.module.common.BaseTime;
import softfocus.space.conference.module.member.enumeration.ProviderType;

import javax.persistence.*;

@Entity @Table(name = "MEMBERS")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("이름")
    @Column(length = 10, nullable = false)
    private String nickname;

    @Comment("이메일")
    @Column(length = 30)
    private String email;

    @OneToOne
    @JoinColumn(name = "oauth_idx", referencedColumnName = "idx")
    private MemberOauth memberOauth;

    private String provider;

    @Enumerated(EnumType.STRING)
    private MemberRoleType role;

    @Comment("위도")
    private Double lat;

    @Comment("경도")
    private Double lon;


    public Member update(String nickname){
        this.nickname = nickname;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
