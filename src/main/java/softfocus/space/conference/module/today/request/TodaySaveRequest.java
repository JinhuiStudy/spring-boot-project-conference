package softfocus.space.conference.module.today.request;

import lombok.Data;
import softfocus.space.conference.module.today.dto.TodayElementDto;

import java.util.List;

@Data
public class TodaySaveRequest {
    private Integer memberIdx;
    private List<TodayElementDto> todayElements;
}
