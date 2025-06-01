package io.github.sergejsvisockis.documentservice.provider;

import io.awspring.cloud.s3.S3Exception;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class S3DocumentProviderTest {

    private final String bucket = "insurtechstorage";

    @Mock
    private S3Client s3Client;

    private S3DocumentProvider s3DocumentProvider;

    @BeforeEach
    void setUp() {
        s3DocumentProvider = new S3DocumentProvider(bucket, s3Client);
    }

    @Test
    void shouldGetDocumentFromBucket() {
        // given
        final String fileName = "test.pdf";
        ResponseInputStream<GetObjectResponse> responseInputStream = mock(ResponseInputStream.class);
        when(s3Client.getObject(any(GetObjectRequest.class))).thenReturn(responseInputStream);

        // when
        ResponseInputStream<GetObjectResponse> result = s3DocumentProvider.getDocument(fileName);

        // then
        assertSame(responseInputStream, result);
        verify(s3Client).getObject(eq(GetObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build()));
        verify(s3Client, times(0)).createBucket(any(CreateBucketRequest.class));
    }

    @Test
    void shouldNotGetDocumentFromTheS3BucketButCreateOneInstead() {
        // given
        final String fileName = "test.pdf";
        when(s3Client.getObject(any(GetObjectRequest.class))).thenThrow(new S3Exception("No message", new Exception()));

        // when
        assertThrows(S3Exception.class, () -> s3DocumentProvider.getDocument(fileName));

        // then
        verify(s3Client).getObject(eq(GetObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build()));
    }

    @Test
    void shouldStoreDocumentIntoTheS3BucketWhenOneExists() {
        // given
        final String fileName = "test.pdf";
        final byte[] document = new byte[]{1, 2, 3};

        // when
        s3DocumentProvider.store(document, fileName);

        // then
        verify(s3Client).putObject(
                eq(PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .contentType(MediaType.APPLICATION_PDF.toString())
                        .build()),
                any(RequestBody.class));
        verify(s3Client, times(0)).createBucket(any(CreateBucketRequest.class));
    }

    @Test
    void shouldCreateBucketAndStoreDocumentWhenNoneExists() {
        // given
        final String fileName = "test.pdf";
        final byte[] document = new byte[]{1, 2, 3};

        // when
        s3DocumentProvider.store(document, fileName);

        // then
        verify(s3Client).putObject(
                eq(PutObjectRequest.builder()
                        .bucket(bucket)
                        .key(fileName)
                        .contentType(MediaType.APPLICATION_PDF.toString())
                        .build()),
                any(RequestBody.class));
    }
}
