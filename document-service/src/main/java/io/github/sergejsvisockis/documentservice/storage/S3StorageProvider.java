package io.github.sergejsvisockis.documentservice.storage;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Component
public class S3StorageProvider implements DocumentStorageProvider<ResponseInputStream<GetObjectResponse>> {

    private static final String BUCKET = "insurtechstorage";

    private final S3Client s3Client;

    public S3StorageProvider(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public ResponseInputStream<GetObjectResponse> getDocument(String fileName) {
        return s3Client.getObject(GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build());
    }
}
