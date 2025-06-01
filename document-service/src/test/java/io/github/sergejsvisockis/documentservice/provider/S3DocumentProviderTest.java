package io.github.sergejsvisockis.documentservice.provider;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3DocumentProviderTest {

    private static final String BUCKET = "insurtechstorage";

    @Mock
    private S3Client s3Client;

    @InjectMocks
    private S3DocumentProvider s3DocumentProvider;

    @Test
    void shouldGetDocumentFromTheS3Bucket() {
        // given
        final String fileName = "test.pdf";
        ResponseInputStream<GetObjectResponse> responseInputStream = mock(ResponseInputStream.class);
        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(responseInputStream);

        // when
        ResponseInputStream<GetObjectResponse> result = s3DocumentProvider.getDocument(fileName);

        // then
        assertSame(responseInputStream, result);
        verify(s3Client).getObject(eq(GetObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build()));
    }

    @Test
    void shouldStoreDocumentIntoTheS3Bucket() {
        // given
        final String fileName = "test.pdf";
        final byte[] document = new byte[]{1, 2, 3};

        // when
        s3DocumentProvider.store(document, fileName);

        // then
        verify(s3Client).putObject(
                eq(PutObjectRequest.builder()
                        .bucket(BUCKET)
                        .key(fileName)
                        .contentType(MediaType.APPLICATION_PDF.toString())
                        .build()),
                any(RequestBody.class));
    }
}
