package io.github.sergejsvisockis.documentservice.api;

import io.github.sergejsvisockis.documentservice.api.model.DocumentReadResponse;
import io.github.sergejsvisockis.documentservice.api.model.DocumentResponse;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.service.DocumentReadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentReadServiceApiImplTest {

    @Mock
    private DocumentReadService documentReadService;

    @Mock
    private DocumentResponseMapper documentResponseMapper;

    @InjectMocks
    private DocumentReadServiceApiImpl documentReadServiceApi;

    @Test
    void getDocumentMetadata_shouldReturnDocumentMetadata() {
        // given
        String documentType = "claim";
        List<Document> documents = List.of(new Document(), new Document());
        List<DocumentResponse> documentResponses = List.of(
                new DocumentResponse().id(UUID.randomUUID()).entityType("claim").fileName("file1.pdf"),
                new DocumentResponse().id(UUID.randomUUID()).entityType("claim").fileName("file2.pdf")
        );

        when(documentReadService.getDocumentMetadata(documentType)).thenReturn(documents);
        when(documentResponseMapper.mapDocumentsToDocumentResponses(documents)).thenReturn(documentResponses);

        // when
        ResponseEntity<DocumentReadResponse> response = documentReadServiceApi.getDocumentMetadata(documentType);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(documentResponses, response.getBody().getDocuments());
        verify(documentReadService).getDocumentMetadata(documentType);
        verify(documentResponseMapper).mapDocumentsToDocumentResponses(documents);
    }

    @Test
    void getDocument_shouldReturnDocumentAsResource() {
        // given
        UUID documentId = UUID.randomUUID();
        String fileName = documentId + ".pdf";
        ResponseInputStream<GetObjectResponse> s3Object = mock(ResponseInputStream.class);

        when(documentReadService.getDocument(fileName)).thenReturn(s3Object);

        // when
        ResponseEntity<Object> response = documentReadServiceApi.getDocument(documentId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
        assertEquals("attachment; filename=\"" + fileName + "\"", 
                response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        verify(documentReadService).getDocument(fileName);
    }
}
