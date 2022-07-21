package softfocus.space.conference.module.today.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import softfocus.space.conference.module.common.BaseTime;
import softfocus.space.conference.module.today.dto.TodayStyleDto;
import softfocus.space.conference.module.today.enumeration.STYLE_SORT;

import javax.persistence.*;

@Entity @Table(name = "TODAY_STYLE")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TodayStyle extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Comment("글 요소 번호")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "element_idx", referencedColumnName = "idx")
    private TodayElement todayElement;

    @Comment("스타일 종류")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private STYLE_SORT styleSort;

    @Comment("스타일 값")
    private String styleValue;

    public TodayStyleDto toDTO(){
        return new TodayStyleDto(idx, styleSort, styleValue);
    }

}
