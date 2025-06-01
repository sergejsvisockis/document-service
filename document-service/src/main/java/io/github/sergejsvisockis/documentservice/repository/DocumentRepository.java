package io.github.sergejsvisockis.documentservice.repository;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.List;
import java.util.Map;

@Repository
public class DocumentRepository {

    private final DynamoDbTemplate dynamoDbTemplate;

    public DocumentRepository(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    public List<Document> findAllByType(String type) {
        ScanEnhancedRequest request = ScanEnhancedRequest.builder()
                .filterExpression(Expression.builder()
                        .expression("entityType = :type")
                        .expressionValues(Map.of(":type", AttributeValue.fromS(type)))
                        .build())
                .build();
        return dynamoDbTemplate.scan(request, Document.class)
                .stream()
                .flatMap(p -> p.items().stream())
                .toList();
    }

    public Document save(Document document) {
        return dynamoDbTemplate.save(document);
    }

}
