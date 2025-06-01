package io.github.sergejsvisockis.documentservice.sns;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

@Component
public class DocumentSNSPublisher {

    private final String topicArn;
    private final SnsClient snsClient;

    public DocumentSNSPublisher(@Value("${spring.cloud.aws.sns.topic-arn}") String topicArn,
                                SnsClient snsClient) {
        this.topicArn = topicArn;
        this.snsClient = snsClient;
    }

    public void publish(String message) {
        snsClient.publish(PublishRequest.builder()
                .topicArn(topicArn)
                .message(message)
                .build());
    }

}
