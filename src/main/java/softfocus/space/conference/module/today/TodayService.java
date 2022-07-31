package softfocus.space.conference.module.today;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.today.entity.Today;
import softfocus.space.conference.module.today.repository.TodayRepository;
import softfocus.space.conference.module.today.vimeo.Vimeo;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodayService {

    private final TodayRepository todayRepository;

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

}
