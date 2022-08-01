package softfocus.space.conference.module.today;


import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import softfocus.space.conference.module.member.MemberService;
import softfocus.space.conference.module.today.dto.FileGrapeDTO;
import softfocus.space.conference.module.today.request.TodaySaveRequest;
import softfocus.space.conference.module.today.response.FileResponse;
import softfocus.space.conference.module.today.file.FileService;
import softfocus.space.conference.module.today.vimeo.VimeoSampleData;
import softfocus.space.conference.module.today.vimeo.VimeoService;
import softfocus.space.conference.module.today.vimeo.response.VimeoRoot;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/today")
@RequiredArgsConstructor
@Slf4j
public class TodayRestController {

    private final FileService fileService;
    private final TodayService todayService;
    private final MemberService memberService;
    private final VimeoService vimeoService;
    private final ObjectMapper objectMapper = new ObjectMapper();

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
        return ResponseEntity.ok(VimeoSampleData.jsonData);
    }

    @PostMapping("/vimeo/upload/{memberIdx}")
    public ResponseEntity<Object> vimeoFileUpload (
            MultipartHttpServletRequest multipartHttpServletRequest,
            @PathVariable Integer memberIdx
    ){
        var file = multipartHttpServletRequest.getFile("file");
        if (file == null) return ResponseEntity.badRequest().build();

        var member = memberService.getMember_paramIdx(memberIdx);
        if (member == null) return ResponseEntity.badRequest().build();

        try {
            vimeoService.vimeoUpload(file, member);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/vimeo/delete/{vimeoId}")
    public ResponseEntity<Object> vimeoFileDelete (
            @PathVariable String vimeoId){
        try {
            vimeoService.vimeoDelete("/videos/" + vimeoId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/vimeo/get/{vimeoId}")
    public ResponseEntity<Object> vimeoFileGet (
            @PathVariable String vimeoId){
        try {
            var jsonString = vimeoService.vimeoGet("/videos/" + vimeoId).getJson().toString();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            var vimeoRoot = objectMapper.readValue(jsonString, VimeoRoot.class);
            return ResponseEntity.ok(vimeoRoot);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
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
