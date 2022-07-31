package softfocus.space.conference.module.today.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import softfocus.space.conference.module.common.BaseTime;
import softfocus.space.conference.module.member.Member;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "TODAY")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class File extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("이름")
    @Column(nullable = false)
    private LocalDate day;

    @Comment("글을 쓴 작가")
    @ManyToOne
    @JoinColumn(name = "member_idx", referencedColumnName = "idx", nullable = false)
    private Member member;

}
