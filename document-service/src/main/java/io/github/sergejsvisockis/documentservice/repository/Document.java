package io.github.sergejsvisockis.documentservice.repository;

import io.github.sergejsvisockis.documentservice.dynamodb.TableName;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import static io.github.sergejsvisockis.documentservice.repository.Document.TABLE_NAME;

@DynamoDbBean
@TableName(name = TABLE_NAME)
public class Document {

    public static final String TABLE_NAME = "document";

    private String documentId;
    private String documentType;
    private String fileName;

    @DynamoDbPartitionKey
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @DynamoDbAttribute("documentType")
    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    @DynamoDbAttribute("fileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String documentName) {
        this.fileName = documentName;
    }
}
