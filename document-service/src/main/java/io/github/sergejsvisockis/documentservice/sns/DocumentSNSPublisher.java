package io.github.sergejsvisockis.documentservice.sns;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.Topic;

@Component
public class DocumentSNSPublisher {

    private final SnsClient snsClient;

    public DocumentSNSPublisher(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    public String createTopicIfNoneExist(String topicName) {
        return snsClient.listTopics()
                .topics()
                .stream()
                .map(Topic::topicArn)
                .filter(s -> s.endsWith(":" + topicName))
                .findFirst()
                .orElse(createTopic(topicName));

    }

    private String createTopic(String topicName) {
        CreateTopicRequest request = CreateTopicRequest.builder()
                .name(topicName)
                .build();
        return snsClient.createTopic(request).topicArn();
    }

    public void publish(String topicArn, String message) {
        snsClient.publish(PublishRequest.builder()
                .topicArn(topicArn)
                .message(message)
                .build());
    }

}
