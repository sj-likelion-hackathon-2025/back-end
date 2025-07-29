package org.chungnamthon.flowmate.infrastructure.s3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@SpringBootTest
class S3ProviderTest {

    @Autowired
    private S3Provider s3Provider;

    @Autowired
    private S3Client s3Client;

    private final String bucket = "kwakmunsu";
    private final String dir = "flowmate/";
    private final int REPEAT = 200; // 100번 반복, 더 많게 조정 가능

    List<String> filenameList = new ArrayList<>();
    MockMultipartFile file;

    @BeforeEach
    void setUp() throws IOException {
        file = getMockMultipartFile();
        for (int i = 0; i < REPEAT; i++) {
            filenameList.add(createFileName(file));
        }
    }

    @DisplayName("getBytes()를 이용한 S3 성능 테스트")
    @Test
    void testGetBytesUploadPerformance() throws Exception {
        System.gc();
        Thread.sleep(200);
        // 시작
        long startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.nanoTime();
        // 업로드
        for (int i = 0; i < REPEAT; i++) {
            RequestBody rb = RequestBody.fromBytes(file.getBytes());
            PutObjectRequest req = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(filenameList.get(i))
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(req, rb);
        }
        // 종료
        long endTime = System.nanoTime();
        long endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.gc();
        Thread.sleep(200);

        System.out.println("getBytes() 방식 총 소요 시간: " + ((endTime - startTime) / 1_000_000) + "ms");
        System.out.println("getBytes() 1회 평균 소요 시간: " + ((endTime - startTime) / 1_000_000.0 / REPEAT) + "ms");
        System.out.println("getBytes() 방식 메모리 사용 증가량: " + ((endMem - startMem) / (1024 * 1024)) + "MB");
        // 삭제
        for (int i = 0; i < REPEAT; i++) {
            s3Provider.deleteImage(filenameList.get(i));
        }

    }

    @DisplayName("InputStream()를 이용한 S3 성능 테스트")
    @Test
    void testGetInputStreamUploadPerformance() throws Exception {
        System.gc();
        Thread.sleep(200);
        // 시작
        long startMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.nanoTime();
        // 업로드
        for (int i = 0; i < REPEAT; i++) {
            InputStream is = file.getInputStream();
            RequestBody rb = RequestBody.fromInputStream(is, file.getSize());
            PutObjectRequest req = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(filenameList.get(i))
                    .contentType(file.getContentType())
                    .build();
            s3Client.putObject(req, rb);
            is.close();
        }
        // 종료
        long endTime = System.nanoTime();
        long endMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        System.gc();
        Thread.sleep(200);

        System.out.println("getInputStream() 방식 총 소요 시간: " + ((endTime - startTime) / 1_000_000) + "ms");
        System.out.println("getInputStream() 1회 평균 소요 시간: " + ((endTime - startTime) / 1_000_000.0 / REPEAT) + "ms");
        System.out.println("getInputStream() 방식 메모리 사용 증가량: " + ((endMem - startMem) / (1024 * 1024)) + "MB");
        // 삭제
        for (int i = 0; i < REPEAT; i++) {
            s3Provider.deleteImage(filenameList.get(i));
        }
    }

    private MockMultipartFile getMockMultipartFile() throws IOException {
        File image = new File("src/test/resources/test.png");
        return new MockMultipartFile(
                "image",                         // 파라미터 이름
                "test.png",                      // 파일 이름
                "image/png",                    // Content-Type
                new FileInputStream(image)
        );
    }

    private String createFileName(MultipartFile file) {
        return dir + UUID.randomUUID() + "-" + file.getOriginalFilename();
    }

}