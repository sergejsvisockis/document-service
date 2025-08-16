package io.github.sergejsvisockis.documentservice.repository;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiKeyRepositoryTest {

    @Mock
    private DynamoDbTemplate dynamoDbTemplate;

    private ApiKeyRepository apiKeyRepository;

    @BeforeEach
    void setUp() {
        apiKeyRepository = new ApiKeyRepository(dynamoDbTemplate);
    }

    @Test
    void shouldScanForApiKey() {
        // given
        String apiKey = "test-api-key";
        PageIterable<ApiKey> pageIterableMock = mock(PageIterable.class);
        when(dynamoDbTemplate.scan(any(), eq(ApiKey.class))).thenReturn(pageIterableMock);

        // when
        apiKeyRepository.findApiKey(apiKey);

        // then
        verify(dynamoDbTemplate).scan(any(), eq(ApiKey.class));
    }
}