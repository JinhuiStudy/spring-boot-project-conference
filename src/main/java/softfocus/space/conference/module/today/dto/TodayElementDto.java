package softfocus.space.conference.module.today.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import softfocus.space.conference.module.today.enumeration.ELEMENT_SORT;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodayElementDto {
    private Long id;
    private ELEMENT_SORT elementSort;
    private Integer rowOrder;
    private Integer columnOrder;
    private List<TodayStyleDto> todayStyles = new ArrayList<>();
}
