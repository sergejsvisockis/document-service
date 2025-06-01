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
    void shouldNotCreateTopicIfOneExists() {
        // given
        String topicName = "test-topic";
        String expectedTopicArn = "arn:aws:sns:us-east-1:123456789012:test-topic";

        // Create a mock CreateTopicResponse in case createTopic is called
        CreateTopicResponse createTopicResponse = CreateTopicResponse.builder()
                .topicArn(expectedTopicArn)
                .build();
        when(snsClient.createTopic(any(CreateTopicRequest.class))).thenReturn(createTopicResponse);

        Topic topic = Topic.builder().topicArn(expectedTopicArn).build();
        ListTopicsResponse listTopicsResponse = ListTopicsResponse.builder()
                .topics(List.of(topic))
                .build();

        when(snsClient.listTopics()).thenReturn(listTopicsResponse);

        // when
        String result = documentSNSPublisher.createTopicIfNoneExist(topicName);

        // then
        assertEquals(expectedTopicArn, result);
        verify(snsClient).listTopics();
    }

    @Test
    void shouldCreateTopicIfNoneExists() {
        // given
        String topicName = "test-topic";
        String expectedTopicArn = "arn:aws:sns:us-east-1:123456789012:test-topic";

        // No matching topic in the list
        Topic otherTopic = Topic.builder().topicArn("arn:aws:sns:us-east-1:123456789012:other-topic").build();
        ListTopicsResponse listTopicsResponse = ListTopicsResponse.builder()
                .topics(List.of(otherTopic))
                .build();

        CreateTopicResponse createTopicResponse = CreateTopicResponse.builder()
                .topicArn(expectedTopicArn)
                .build();

        when(snsClient.listTopics()).thenReturn(listTopicsResponse);
        when(snsClient.createTopic(any(CreateTopicRequest.class))).thenReturn(createTopicResponse);

        // when
        String result = documentSNSPublisher.createTopicIfNoneExist(topicName);

        // then
        assertEquals(expectedTopicArn, result);
        verify(snsClient).listTopics();
        verify(snsClient).createTopic(any(CreateTopicRequest.class));
    }

    @Test
    void shouldPublishMessageIntoTheTopic() {
        // given
        String topicArn = "arn:aws:sns:us-east-1:123456789012:test-topic";
        String message = "test-message";

        PublishResponse publishResponse = PublishResponse.builder().build();
        when(snsClient.publish(any(PublishRequest.class))).thenReturn(publishResponse);

        // when
        documentSNSPublisher.publish(topicArn, message);

        // then
        verify(snsClient).publish(any(PublishRequest.class));
    }
}
