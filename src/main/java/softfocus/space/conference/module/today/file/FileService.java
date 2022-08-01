package softfocus.space.conference.module.today.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import softfocus.space.conference.module.member.Member;
import softfocus.space.conference.module.today.entity.File;
import softfocus.space.conference.module.today.repository.FileRepository;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FileService {

    @Value("${cloud.aws.s3.bucket}")
    String bucket;

    @Value("${cloud.aws.s3.location}")
    String path;
    private final AmazonS3 amazonS3;
    private final FileRepository fileRepository;

    @Transactional
    public File saveFile(MultipartFile multipartFile, Member member) throws IOException{
        var image = ImageIO.read(multipartFile.getInputStream());
        var fileName = createRandomFileName() + "_" + getCurrentDateTimeConnect();
        var path = uploadFile(multipartFile, fileName);
        return fileRepository.save(
                new File(
                        null,
                        member,
                        multipartFile.getOriginalFilename(),
                        multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1),
                        fileName,
                        path,
                        image == null ? 100 : image.getHeight(),
                        image == null ? 100 : image.getWidth(),
                        multipartFile.getSize()
                )
        );
    }

    public String uploadFile(MultipartFile multipartFile, String fileName) throws IOException {
        var objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        var putObjectRequest = new PutObjectRequest(
                bucket,
                path + fileName,
                multipartFile.getInputStream(),
                objectMetadata
        );

        amazonS3.putObject(putObjectRequest);
        return amazonS3.getUrl(putObjectRequest.getBucketName(), putObjectRequest.getKey())
                .toString()
                .replace("https", "http");
    }

    private String getCurrentDateTimeConnect() {
        var current = LocalDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return current.format(formatter);
    }

    private String createRandomFileName() {
        return UUID.randomUUID().toString();
    }

}
