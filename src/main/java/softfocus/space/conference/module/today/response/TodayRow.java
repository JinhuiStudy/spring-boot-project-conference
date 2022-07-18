package softfocus.space.conference.module.today.response;

import lombok.Data;
import softfocus.space.conference.module.today.dto.TodayElementDto;

import java.util.ArrayList;
import java.util.List;

@Data
public class TodayRow {
    private Integer row;
    private List<TodayElementDto> todayElements = new ArrayList<>();
}
