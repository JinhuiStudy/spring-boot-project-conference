package softfocus.space.conference.module.today.vimeo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.today.entity.Vimeo;
import softfocus.space.conference.module.today.repository.VimeoRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VimeoService {

    private final VimeoRepository vimeoRepository;

    private final String token = "f989153b326644a3f929f3e2d867d92f";

    @Transactional
    public VimeoResponse vimeoUpload(MultipartFile multipartFile, Member member) throws Exception {
        var vimeoUtil = new VimeoUtil(token);

        var file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        boolean isFile = file.createNewFile();
        var fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();

        var videoEndPoint = vimeoUtil.addVideo(file);
        log.info("VideoEndPoint: {}", videoEndPoint);

        vimeoRepository.save(
                new Vimeo(null, member, videoEndPoint)
        );

        //get video info
        var info = vimeoUtil.getVideoInfo(videoEndPoint);
        log.info("Video info: {}", info.toString());

        //edit video
        var name = "Video TEST";
        var desc = "Description";
        var license = ""; //see Vimeo API Documentation
        var privacyView = "disable"; //see Vimeo API Documentation
        var privacyEmbed = "whitelist"; //see Vimeo API Documentation
        var reviewLink = false;

        return vimeoUtil.updateVideoMetadata(videoEndPoint, name, desc, license, privacyView, privacyEmbed, reviewLink);

        //add video privacy domain
//        vimeo.addVideoPrivacyDomain(videoEndPoint, "clickntap.com");
    }

    @Transactional
    public VimeoResponse vimeoDelete(String videoEndPoint) throws Exception {
        var vimeoUtil = new VimeoUtil(token);
        //delete video
        VimeoResponse vimeoResponse = vimeoUtil.removeVideo(videoEndPoint);
        vimeoRepository.deleteByEndPoint(videoEndPoint);
        return vimeoResponse;
    }

    public VimeoResponse vimeoGet(String videoEndPoint) throws Exception {
        var vimeoUtil = new VimeoUtil(token);

        //delete video
        return vimeoUtil.get(videoEndPoint);
    }

}
