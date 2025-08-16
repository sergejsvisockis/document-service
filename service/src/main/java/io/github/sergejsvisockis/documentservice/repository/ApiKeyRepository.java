package io.github.sergejsvisockis.documentservice.repository;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ApiKeyRepository {

    private final DynamoDbTemplate dynamoDbTemplate;

    public Optional<ApiKey> findApiKey(String key) {
        ScanEnhancedRequest request = ScanEnhancedRequest.builder()
                .filterExpression(Expression.builder()
                        .expression("apiKey = :key")
                        .expressionValues(Map.of(":key", AttributeValue.fromS(key)))
                        .build())
                .build();
        return dynamoDbTemplate.scan(request, ApiKey.class)
                .stream()
                .flatMap(p -> p.items().stream())
                .findFirst();
    }

}
