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
    private Integer id;

    @Comment("이름")
    @Column(length = 10, nullable = false)
    private String name;

}
