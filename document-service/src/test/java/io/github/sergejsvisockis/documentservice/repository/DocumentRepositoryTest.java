package io.github.sergejsvisockis.documentservice.repository;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentRepositoryTest {

    @Mock
    private DynamoDbClient dynamoDbClient;

    @Mock
    private DynamoDbEnhancedClient enhancedClient;

    @Mock
    private DynamoDbTemplate dynamoDbTemplate;

    private DocumentRepository documentRepository;

    @BeforeEach
    void setUp() {
        documentRepository = new DocumentRepository(
                dynamoDbClient,
                enhancedClient,
                dynamoDbTemplate
        );
    }

    @Test
    void shouldFindAllDocumentsByType() {
        // given
        PageIterable pageIterableMock = mock(PageIterable.class);
        when(dynamoDbTemplate.scan(any(), any())).thenReturn(pageIterableMock);

        // when
        documentRepository.findAllByType("claim");

        // then
        verify(dynamoDbTemplate).scan(any(), any());
    }

    @Test
    void shouldSaveDocumentAndReturnThat() {
        // given
        Document document = new Document();
        document.setDocumentId("1");
        document.setEntityType("claim");
        document.setFileName("test.pdf");

        when(dynamoDbTemplate.save(document)).thenReturn(document);

        // when
        Document result = documentRepository.save(document);

        // then
        assertSame(document, result);
        verify(dynamoDbTemplate).save(document);
    }
}
