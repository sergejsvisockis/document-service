package io.github.sergejsvisockis.documentservice.repository;

import io.github.sergejsvisockis.documentservice.dynamodb.TableName;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import static io.github.sergejsvisockis.documentservice.repository.ApiKey.TABLE_NAME;

@Setter
@DynamoDbBean
@TableName(name = TABLE_NAME)
public class ApiKey {

    public static final String TABLE_NAME = "api_key";

    private String apiKeyId;
    private String apiKey;
    private String salt;
    private String assignee;
    private String assigneeContactDetails;
    private String expirationDate;

    @DynamoDbPartitionKey
    public String getApiKeyId() {
        return apiKeyId;
    }

    @DynamoDbAttribute("apiKey")
    public String getApiKey() {
        return apiKey;
    }

    @DynamoDbAttribute("salt")
    public String getSalt() {
        return salt;
    }

    @DynamoDbAttribute("assignee")
    public String getAssignee() {
        return assignee;
    }

    @DynamoDbAttribute("assigneeContactDetails")
    public String getAssigneeContactDetails() {
        return assigneeContactDetails;
    }

    @DynamoDbAttribute("expirationDate")
    public String getExpirationDate() {
        return expirationDate;
    }
}
