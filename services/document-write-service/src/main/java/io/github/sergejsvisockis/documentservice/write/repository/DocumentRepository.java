package io.github.sergejsvisockis.documentservice.write.repository;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import static io.github.sergejsvisockis.documentservice.write.repository.Document.TABLE_NAME;

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

    public Document save(Document document) {
        return dynamoDbTemplate.save(document);
    }

}
