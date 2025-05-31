package io.github.sergejsvisockis.documentservice.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Component
@RequiredArgsConstructor
public class S3DocumentProvider implements DocumentProvider<ResponseInputStream<GetObjectResponse>> {

    private static final String BUCKET = "insurtechstorage";

    private final S3Client s3Client;

    @Override
    public ResponseInputStream<GetObjectResponse> getDocument(String fileName) {
        return s3Client.getObject(GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build());
    }
}
