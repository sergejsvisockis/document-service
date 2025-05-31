package io.github.sergejsvisockis.documentservice.repository;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.Expression;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.ScanEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.util.List;
import java.util.Map;

import static io.github.sergejsvisockis.documentservice.repository.Document.TABLE_NAME;

@Repository
public class DocumentRepository {

    private final DynamoDbTemplate dynamoDbTemplate;

    public DocumentRepository(DynamoDbClient dynamoDbClient,
                              DynamoDbEnhancedClient enhancedClient,
                              DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;

        try {
            dynamoDbClient.describeTable(b -> b.tableName(TABLE_NAME));
        } catch (ResourceNotFoundException e) {
            enhancedClient.table(TABLE_NAME,
                            TableSchema.fromClass(Document.class))
                    .createTable();
        }
    }

    public List<Document> findAllByType(String type) {
        ScanEnhancedRequest request = ScanEnhancedRequest.builder()
                .filterExpression(Expression.builder()
                        .expression("documentType = :type")
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
