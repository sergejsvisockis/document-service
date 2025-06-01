package io.github.sergejsvisockis.documentservice.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3DocumentProvider implements DocumentProvider<ResponseInputStream<GetObjectResponse>, byte[]> {

    private final String bucket;
    private final S3Client s3Client;

    public S3DocumentProvider(@Value("${spring.cloud.aws.s3.bucket}") String bucket,
                              S3Client s3Client) {
        this.bucket = bucket;
        this.s3Client = s3Client;
    }

    @Override
    public ResponseInputStream<GetObjectResponse> getDocument(String fileName) {
        return s3Client.getObject(GetObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build());
    }

    @Override
    public void store(byte[] document, String fileName) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .contentType(MediaType.APPLICATION_PDF.toString())
                        .build(),
                RequestBody.fromBytes(document)
        );
    }
}
