package softfocus.space.conference.module.today.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import softfocus.space.conference.module.today.enumeration.STYLE_SORT;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodayStyleDto {
    private Long id;
    private STYLE_SORT styleSort;
    private String styleValue;
}
