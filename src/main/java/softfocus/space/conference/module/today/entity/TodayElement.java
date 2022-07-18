package softfocus.space.conference.module.today.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import softfocus.space.conference.module.common.BaseTime;
import softfocus.space.conference.module.today.dto.TodayElementDto;
import softfocus.space.conference.module.today.enumeration.ELEMENT_SORT;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name = "TODAY_ELEMENT")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TodayElement extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("글 번호")
    @ManyToOne
    @JoinColumn(name = "today_idx", referencedColumnName = "idx", nullable = false)
    private Today today;

    @Comment("요소 종류")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ELEMENT_SORT elementSort;

    @Comment("열 순번")
    @Column(nullable = false, name = "row_order")
    private Integer rowOrder;

    @Comment("컬럼 순번")
    @Column(nullable = false, name = "column_order")
    private Integer columnOrder;

    @OneToMany(mappedBy = "todayElement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodayStyle> todayStyles = new ArrayList<>();

    public void addStyle(TodayStyle todayStyle) {
        todayStyles.add(todayStyle);
        todayStyle.setTodayElement(this);
    }

    public void removeStyle(TodayStyle todayStyle) {
        todayStyles.remove(todayStyle);
        todayStyle.setTodayElement(null);
    }

    public TodayElementDto toDTO() {
        return new TodayElementDto(
                id, elementSort, rowOrder, columnOrder,
                todayStyles.stream().map(TodayStyle::toDTO).toList()
        );
    }
}
