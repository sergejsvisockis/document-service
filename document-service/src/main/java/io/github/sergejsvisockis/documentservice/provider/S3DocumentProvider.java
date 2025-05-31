package io.github.sergejsvisockis.documentservice.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;

@Component
@RequiredArgsConstructor
public class S3DocumentProvider implements DocumentProvider<ResponseInputStream<GetObjectResponse>, ByteArrayOutputStream> {

    private static final String BUCKET = "insurtechstorage";

    private final S3Client s3Client;

    @Override
    public ResponseInputStream<GetObjectResponse> getDocument(String fileName) {
        return s3Client.getObject(GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build());
    }

    @Override
    public void store(ByteArrayOutputStream document, String fileName) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(BUCKET)
                        .key(fileName)
                        .contentType(MediaType.APPLICATION_PDF.toString())
                        .build(),
                RequestBody.fromBytes(document.toByteArray())
        );
    }
}
