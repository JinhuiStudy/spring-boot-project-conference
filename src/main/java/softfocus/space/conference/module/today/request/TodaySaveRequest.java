package softfocus.space.conference.module.today.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodaySaveRequest {
    private String htmlData;
    private String cssData;
    private String jsData;
}
