package softfocus.space.conference.module.today;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.today.dto.TodayElementDto;
import softfocus.space.conference.module.today.dto.TodayStyleDto;
import softfocus.space.conference.module.today.entity.Today;
import softfocus.space.conference.module.today.repository.TodayElementRepository;
import softfocus.space.conference.module.today.repository.TodayRepository;
import softfocus.space.conference.module.today.repository.TodayStyleRepository;
import softfocus.space.conference.module.today.response.TodayResponse;
import softfocus.space.conference.module.today.response.TodayRow;
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
    private final TodayElementRepository todayElementRepository;
    private final TodayStyleRepository todayStyleRepository;

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

    /**
     *
     * 컨퍼런스 글 저장
     * @param todayElements 저장 요소 리스트
     * @param member 작가 정보
     * @return TodayResponse
     */
    @Transactional
    public TodayResponse saveToday(List<TodayElementDto> todayElements, Member member) {
        var todayResponse = new TodayResponse();
        if ( todayElements == null || todayElements.size() == 0 ) {
            todayResponse.setIsSuccess(false);
            todayResponse.setMessage("저장할 데이터가 없습니다.");
            return todayResponse;
        }

        var today = todayRepository.save(
                new Today(null, LocalDate.now(), member)
        );
        todayElements.forEach(todayElementDto -> {
            var todayElement = todayElementRepository.save(todayElementDto.toEntity(today));
            todayElementDto.getTodayStyles().forEach(todayStyleDto -> todayElement.addStyle(todayStyleDto.toEntity()));
        });
        return getTodayResponse(todayResponse, today);
    }


    /**
     * 컨퍼런스 글 조회 [일자]
     * @param yyyyMmDd 일자
     * @return TodayResponse
     */
    public TodayResponse getToday (String yyyyMmDd) {
        var todayResponse = new TodayResponse();
        var formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        var date = LocalDate.parse(yyyyMmDd, formatter);

        var todayOptional = todayRepository.findByDay(date);
        if (todayOptional.isEmpty()) {
            todayResponse.setIsSuccess(false);
            todayResponse.setMessage("해당날에 글이 없습니다.");
            return todayResponse;
        }

        return getTodayResponse(todayResponse, todayOptional.get());
    }

    private TodayResponse getTodayResponse(TodayResponse todayResponse, Today today) {
        var sort = Sort.by(Sort.Direction.ASC, "row_order")
                .and(Sort.by(Sort.Direction.ASC, "column_order"));

        var todayElementList = todayElementRepository.findByToday_Id(today.getId(), sort);

        var rowOrder = 0;
        var todayRowList = new ArrayList<TodayRow>();
        TodayRow todayRow = null;

        for (int i = 0; i < todayElementList.size(); i++) {
            var todayElement = todayElementList.get(i);
            var elementRowOrder = todayElement.getRowOrder();

            if (rowOrder != elementRowOrder) {
                if (i != 0) {
                    todayRowList.add(todayRow);
                }
                rowOrder = elementRowOrder;
                todayRow = new TodayRow();
                todayRow.setRow(elementRowOrder);
            }

            if (todayRow != null) {
                todayRow.getTodayElements().add(todayElement.toDTO());
            }

            if (todayElementList.size() - 1 == i) {
                todayRowList.add(todayRow);
                break;
            }
        }
        todayResponse.setTodayRows(todayRowList);
        return todayResponse;
    }

    @Transactional
    public boolean deleteStyle(Long todayStyleId){
        var todayStyleOptional = todayStyleRepository.findById(todayStyleId);
        if (todayStyleOptional.isEmpty()) {
            return false;
        }
        todayStyleRepository.delete(todayStyleOptional.get());
        return true;
    }

    @Transactional
    public boolean deleteElement(Long todayElementId){
        var todayElementOptional = todayElementRepository.findById(todayElementId);
        if (todayElementOptional.isEmpty()) {
            return false;
        }
        todayElementRepository.delete(todayElementOptional.get());
        return true;
    }

}
