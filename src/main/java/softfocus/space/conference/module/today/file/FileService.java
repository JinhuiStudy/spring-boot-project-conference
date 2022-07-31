package softfocus.space.conference.module.today.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    @Value("${cloud.aws.s3.bucket}")
    String bucket;

    @Value("${cloud.aws.s3.location}")
    String path;
    private final AmazonS3 amazonS3;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        var objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        var putObjectRequest = new PutObjectRequest(
                bucket,
                path + createRandomFileName() + "_" + getCurrentDateTimeConnect(),
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
