package softfocus.space.conference.module.today;


import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import softfocus.space.conference.module.member.MemberService;
import softfocus.space.conference.module.today.dto.FileGrapeDTO;
import softfocus.space.conference.module.today.entity.Today;
import softfocus.space.conference.module.today.request.TodaySaveRequest;
import softfocus.space.conference.module.today.response.FileResponse;
import softfocus.space.conference.module.today.file.FileService;
import softfocus.space.conference.module.today.vimeo.VimeoSampleData;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/today")
@RequiredArgsConstructor
public class TodayRestController {

    private final FileService fileService;
    private final TodayService todayService;
    private final MemberService memberService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AmazonS3 amazonS3;

    @PatchMapping("/{idx}")
    public Map<Object, Object> patch(
            @RequestBody Map<Object, Object> map,
            @PathVariable Long idx
    ) {
        try {
            var jsonStr = objectMapper.writeValueAsString(map);
            var resultData = todayService.updateData(jsonStr, idx);
            if (resultData == null) {
                return map;
            }
            return resultData;
        } catch (JsonProcessingException e) {
            return map;
        }
    }

    @PostMapping("/{idx}")
    public ResponseEntity<Object> post(
            @RequestBody TodaySaveRequest todaySaveRequest,
            @PathVariable Long idx
    ) {
        var today = todayService.save(todaySaveRequest, idx);
        if (today == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("저장을 성공했습니다.");
    }

    @GetMapping("/{idx}")
    public Map<Object, Object> get(@PathVariable Long idx) {
        return todayService.getTodayData(idx);
    }

    @GetMapping("/vimeo/data/{memberIdx}")
    public ResponseEntity<Object> data (
            @PathVariable Integer memberIdx
    ){
        System.out.println("DATA:" + memberIdx);
        return ResponseEntity.ok(VimeoSampleData.jsonData);
    }

    @PostMapping("/vimeo/upload/{memberIdx}")
    public ResponseEntity<Object> vimeoFileUpload (
            MultipartHttpServletRequest multipartHttpServletRequest,
            @PathVariable Integer memberIdx
    ){
        System.out.println("vimeoFileUpload:" + memberIdx);
        var file = multipartHttpServletRequest.getFile("file");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/file/upload/{memberIdx}")
    public ResponseEntity<Object> uploadMultipleFiles(
            MultipartHttpServletRequest multipartHttpServletRequest,
            @PathVariable Integer memberIdx) {

          var member = memberService.getMember_paramIdx(memberIdx);
          if (member == null) {
              return ResponseEntity.ok(
                      new FileResponse(
                              List.of(new FileGrapeDTO("", "image", "", 100, 100))
                      )
              );
          }

          var files = multipartHttpServletRequest.getFiles("files");
          var fileList = files.stream()
                  .map(
                          multipartFile -> {
                              try {
                                  var uploadPath = fileService.saveFile(multipartFile, member);
                                  return uploadPath.toGrapeDTO();
                              } catch (IOException e) {
                                  e.printStackTrace();
                                  return new FileGrapeDTO(multipartFile.getOriginalFilename(), "image", "", 100, 100);
                              }
                          });

      return ResponseEntity.ok(
              new FileResponse(fileList.collect(Collectors.toList()))
      );
    }
}
