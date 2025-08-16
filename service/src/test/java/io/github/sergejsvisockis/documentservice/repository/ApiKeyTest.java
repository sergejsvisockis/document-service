package io.github.sergejsvisockis.documentservice.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiKeyTest {

    @Test
    void shouldSetAndGetApiKeyId() {
        // given
        ApiKey apiKey = new ApiKey();
        String apiKeyId = "test-api-key-id";

        // when
        apiKey.setApiKeyId(apiKeyId);

        // then
        assertEquals(apiKeyId, apiKey.getApiKeyId());
    }

    @Test
    void shouldSetAndGetApiKey() {
        // given
        ApiKey apiKey = new ApiKey();
        String apiKeyValue = "test-api-key";

        // when
        apiKey.setApiKey(apiKeyValue);

        // then
        assertEquals(apiKeyValue, apiKey.getApiKey());
    }

    @Test
    void shouldSetAndGetAssignee() {
        // given
        ApiKey apiKey = new ApiKey();
        String assignee = "Test User";

        // when
        apiKey.setAssignee(assignee);

        // then
        assertEquals(assignee, apiKey.getAssignee());
    }

    @Test
    void shouldSetAndGetAssigneeContactDetails() {
        // given
        ApiKey apiKey = new ApiKey();
        String contactDetails = "test@example.com";

        // when
        apiKey.setAssigneeContactDetails(contactDetails);

        // then
        assertEquals(contactDetails, apiKey.getAssigneeContactDetails());
    }

    @Test
    void shouldSetAndGetExpirationDate() {
        // given
        ApiKey apiKey = new ApiKey();
        String expirationDate = "2025-08-16T15:12:00";

        // when
        apiKey.setExpirationDate(expirationDate);

        // then
        assertEquals(expirationDate, apiKey.getExpirationDate());
    }
}