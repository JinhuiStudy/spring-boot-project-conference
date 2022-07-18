package softfocus.space.conference.module.today.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TodayResponse {
    private Boolean isSuccess = true;
    private String message = "";
    private List<TodayRow> todayRows = new ArrayList<>();
}
