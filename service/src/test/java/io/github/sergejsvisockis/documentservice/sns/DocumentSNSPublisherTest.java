package io.github.sergejsvisockis.documentservice.sns;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentSNSPublisherTest {

    @Mock
    private SnsClient snsClient;

    @InjectMocks
    private DocumentSNSPublisher documentSNSPublisher;

    @Test
    void shouldPublishMessageIntoTheTopic() {
        // given
        String message = "test-message";

        PublishResponse publishResponse = PublishResponse.builder().build();
        when(snsClient.publish(any(PublishRequest.class))).thenReturn(publishResponse);

        // when
        documentSNSPublisher.publish(message);

        // then
        verify(snsClient).publish(any(PublishRequest.class));
    }
}
