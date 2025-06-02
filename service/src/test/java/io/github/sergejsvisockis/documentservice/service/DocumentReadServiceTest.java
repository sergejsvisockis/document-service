package io.github.sergejsvisockis.documentservice.service;

import io.github.sergejsvisockis.documentservice.provider.DocumentProvider;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentReadServiceTest {

    @Mock
    private DocumentProvider<ResponseInputStream<GetObjectResponse>, byte[]> documentProvider;

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentReadService documentReadService;

    @Mock
    private ResponseInputStream<GetObjectResponse> responseInputStream;

    @Test
    void shouldGetDocumentMetadata() {
        // given
        String type = "claim";
        List<Document> expectedDocuments = List.of(new Document(), new Document());
        when(documentRepository.findAllByType(type)).thenReturn(expectedDocuments);

        // when
        List<Document> result = documentReadService.getDocumentMetadata(type);

        // then
        assertEquals(expectedDocuments, result);
        verify(documentRepository).findAllByType(type);
    }

    @Test
    void shouldGetDocument() {
        // given
        String fileName = "test.pdf";
        when(documentProvider.getDocument(fileName)).thenReturn(responseInputStream);

        // when
        ResponseInputStream<GetObjectResponse> result = documentReadService.getDocument(fileName);

        // then
        assertSame(responseInputStream, result);
        verify(documentProvider).getDocument(fileName);
    }
}
