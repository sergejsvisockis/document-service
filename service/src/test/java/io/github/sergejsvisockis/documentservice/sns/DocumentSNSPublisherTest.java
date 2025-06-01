package io.github.sergejsvisockis.documentservice.sns;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.ListTopicsResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.Topic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
