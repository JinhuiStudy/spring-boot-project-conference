package softfocus.space.conference.module.today;


import com.amazonaws.services.s3.AmazonS3;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import softfocus.space.conference.module.today.dto.FileGrapeDTO;
import softfocus.space.conference.module.today.entity.Today;
import softfocus.space.conference.module.today.request.TodaySaveRequest;
import softfocus.space.conference.module.today.response.FileResponse;
import softfocus.space.conference.module.today.file.FileService;
import softfocus.space.conference.module.today.vimeo.VimeoSampleData;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/today")
@RequiredArgsConstructor
public class TodayRestController {

    private final FileService fileService;
    private final TodayService todayService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static Map<Object, Object> map = null;

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

    @GetMapping("/vimeo/data")
    public ResponseEntity<Object> data (){
        return ResponseEntity.ok(VimeoSampleData.jsonData);
    }

    @PostMapping("/vimeo/upload")
    public ResponseEntity<Object> vimeoFileUpload (MultipartHttpServletRequest multipartHttpServletRequest){
        var file = multipartHttpServletRequest.getFile("file");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/file/upload")
    public ResponseEntity<Object> uploadMultipleFiles(MultipartHttpServletRequest multipartHttpServletRequest) {
      var files = multipartHttpServletRequest.getFiles("files");
      var fileList = files.stream().map(multipartFile -> {
          try {
              var image = ImageIO.read(multipartFile.getInputStream());
              try {
                  var uploadPath = fileService.uploadFile(multipartFile);
                  return new FileGrapeDTO(multipartFile.getOriginalFilename(), "image", uploadPath, image.getHeight(), image.getWidth());
              } catch (IOException e) {
                  e.printStackTrace();
                  return new FileGrapeDTO(multipartFile.getOriginalFilename(), "image", "", 100, 100);
              }
          } catch (IOException e) {
              e.printStackTrace();
          }
          return new FileGrapeDTO(multipartFile.getOriginalFilename(), "image", "", 100, 100);
      });

      return ResponseEntity.ok(new FileResponse(fileList.collect(Collectors.toList())));
    }
}
