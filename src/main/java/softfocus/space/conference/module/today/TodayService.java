package softfocus.space.conference.module.today;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.today.entity.Today;
import softfocus.space.conference.module.today.repository.TodayRepository;
import softfocus.space.conference.module.today.request.TodaySaveRequest;
import softfocus.space.conference.module.today.vimeo.Vimeo;

import java.io.File;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodayService {

    private final TodayRepository todayRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void vimeoSample() throws Exception {
        var vimeo = new Vimeo("[token]");

        //add a video
        var videoEndPoint = vimeo.addVideo(new File("/Users/tmendici/Downloads/Video.AVI"));

        //get video info
        var info = vimeo.getVideoInfo(videoEndPoint);
        log.info("Video info: {}", info.toString());

        //edit video
        var name = "Name";
        var desc = "Description";
        var license = ""; //see Vimeo API Documentation
        var privacyView = "disable"; //see Vimeo API Documentation
        var privacyEmbed = "whitelist"; //see Vimeo API Documentation
        var reviewLink = false;
        vimeo.updateVideoMetadata(videoEndPoint, name, desc, license, privacyView, privacyEmbed, reviewLink);

        //add video privacy domain
        vimeo.addVideoPrivacyDomain(videoEndPoint, "clickntap.com");

        //delete video
        vimeo.removeVideo(videoEndPoint);
    }

    @Transactional
    public Today getToday(Member member) {
        var todayOptional = todayRepository.findByDayAndMember_Idx(LocalDate.now(), member.getIdx());
        if (todayOptional.isEmpty()) {
            return todayRepository.save(
                    new Today(null, LocalDate.now(), member, "", "", "", "")
            );
        }
        return todayOptional.get();
    }

    @Transactional
    public Map<Object, Object> updateData(String jsonData, Long idx) {
        var todayOptional = todayRepository.findById(idx);
        todayOptional.ifPresent(today -> today.setData(jsonData));
        try {
            return objectMapper.readValue(jsonData, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public Map<Object, Object> getTodayData(Long idx) {
        var todayOptional = todayRepository.findById(idx);
        if (todayOptional.isEmpty()) return new HashMap<>();
        try {
            return objectMapper.readValue(todayOptional.get().getData(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            return new HashMap<>();
        }
    }

    @Transactional
    public Today save(TodaySaveRequest todaySaveRequest, Long idx) {
        var todayOptional = todayRepository.findById(idx);
        if (todayOptional.isEmpty()) return null;

        var today = todayOptional.get();
//        today.setData(todaySaveRequest.getData());
        today.setCssData(todaySaveRequest.getCssData());
        today.setJsData(todaySaveRequest.getJsData());
        today.setHtmlData(todaySaveRequest.getHtmlData());
        return today;
    }
}
