package io.github.sergejsvisockis.documentservice.write.dynamodb.entity;

import io.github.sergejsvisockis.documentservice.write.dynamodb.TableName;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@TableName
public class DocumentMetadata {

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

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String documentName) {
        this.fileName = documentName;
    }
}
