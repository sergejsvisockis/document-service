package io.github.sergejsvisockis.documentservice.repository;

import io.github.sergejsvisockis.documentservice.dynamodb.TableName;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import static io.github.sergejsvisockis.documentservice.repository.Document.TABLE_NAME;

@Setter
@DynamoDbBean
@TableName(name = TABLE_NAME)
public class Document {

    public static final String TABLE_NAME = "documentAsBytes";

    private String documentId;
    private String documentType;
    private String fileName;

    @DynamoDbPartitionKey
    public String getDocumentId() {
        return documentId;
    }

    @DynamoDbAttribute("entityType")
    public String getDocumentType() {
        return documentType;
    }

    @DynamoDbAttribute("fileName")
    public String getFileName() {
        return fileName;
    }

}
