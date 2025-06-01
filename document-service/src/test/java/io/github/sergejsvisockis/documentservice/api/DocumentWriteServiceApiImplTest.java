package io.github.sergejsvisockis.documentservice.api;

import io.github.sergejsvisockis.documentservice.api.model.ClaimDocumentRequest;
import io.github.sergejsvisockis.documentservice.api.model.DocumentResponse;
import io.github.sergejsvisockis.documentservice.api.model.InvoiceDocumentRequest;
import io.github.sergejsvisockis.documentservice.api.model.PolicyDocumentRequest;
import io.github.sergejsvisockis.documentservice.service.ClaimDocumentWriteService;
import io.github.sergejsvisockis.documentservice.service.InvoiceDocumentWriteService;
import io.github.sergejsvisockis.documentservice.service.PolicyDocumentWriteService;
import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentWriteServiceApiImplTest {

    @Mock
    private DocumentResponseMapper mapper;

    @Mock
    private PolicyDocumentWriteService policyDocumentWriteService;

    @Mock
    private ClaimDocumentWriteService claimDocumentWriteService;

    @Mock
    private InvoiceDocumentWriteService invoiceDocumentWriteService;

    @InjectMocks
    private DocumentWriteServiceApiImpl documentWriteServiceApi;

    @Test
    void shouldWriteClaimsDocument() {
        // given
        ClaimDocumentRequest request = new ClaimDocumentRequest();
        UUID documentId = UUID.randomUUID();
        SentDocumentMetadata metadata = new SentDocumentMetadata(documentId, "claim", documentId + ".pdf");
        DocumentResponse expectedResponse = new DocumentResponse()
                .id(documentId)
                .entityType("claim")
                .fileName(documentId + ".pdf");

        when(claimDocumentWriteService.process(request)).thenReturn(metadata);
        when(mapper.mapMetadataToDocumentResponse(metadata)).thenReturn(expectedResponse);

        // when
        ResponseEntity<DocumentResponse> response = documentWriteServiceApi.writeClaimDocument(request);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
        verify(claimDocumentWriteService).process(request);
        verify(mapper).mapMetadataToDocumentResponse(metadata);
    }

    @Test
    void shouldWriteInvoiceDocument() {
        // given
        InvoiceDocumentRequest request = new InvoiceDocumentRequest();
        UUID documentId = UUID.randomUUID();
        SentDocumentMetadata metadata = new SentDocumentMetadata(documentId, "invoice", documentId + ".pdf");
        DocumentResponse expectedResponse = new DocumentResponse()
                .id(documentId)
                .entityType("invoice")
                .fileName(documentId + ".pdf");

        when(invoiceDocumentWriteService.process(request)).thenReturn(metadata);
        when(mapper.mapMetadataToDocumentResponse(metadata)).thenReturn(expectedResponse);

        // when
        ResponseEntity<DocumentResponse> response = documentWriteServiceApi.writeInvoiceDocument(request);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
        verify(invoiceDocumentWriteService).process(request);
        verify(mapper).mapMetadataToDocumentResponse(metadata);
    }

    @Test
    void shouldWritePolicyDocument() {
        // given
        PolicyDocumentRequest request = new PolicyDocumentRequest();
        UUID documentId = UUID.randomUUID();
        SentDocumentMetadata metadata = new SentDocumentMetadata(documentId, "policy", documentId + ".pdf");
        DocumentResponse expectedResponse = new DocumentResponse()
                .id(documentId)
                .entityType("policy")
                .fileName(documentId + ".pdf");

        when(policyDocumentWriteService.process(request)).thenReturn(metadata);
        when(mapper.mapMetadataToDocumentResponse(metadata)).thenReturn(expectedResponse);

        // when
        ResponseEntity<DocumentResponse> response = documentWriteServiceApi.writePolicyDocument(request);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedResponse, response.getBody());
        verify(policyDocumentWriteService).process(request);
        verify(mapper).mapMetadataToDocumentResponse(metadata);
    }
}
