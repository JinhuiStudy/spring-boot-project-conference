package softfocus.space.conference.module.today.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import softfocus.space.conference.module.common.BaseTime;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.today.dto.FileGrapeDTO;

import javax.persistence.*;

@Entity @Table(name = "VIMEO")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Vimeo extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("비디오 소유자")
    @ManyToOne
    @JoinColumn(name = "member_idx", referencedColumnName = "idx", nullable = false)
    private Member member;

    @Comment("비메오 경로")
    private String endPoint;

}
