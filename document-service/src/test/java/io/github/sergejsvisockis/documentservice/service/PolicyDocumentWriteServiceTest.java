package io.github.sergejsvisockis.documentservice.service;

import com.sergejs.documentservice.api.model.PolicyDocumentRequest;
import io.github.sergejsvisockis.documentservice.pdf.GeneratedPdfHolder;
import io.github.sergejsvisockis.documentservice.pdf.PdfGenerator;
import io.github.sergejsvisockis.documentservice.provider.S3DocumentProvider;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import io.github.sergejsvisockis.documentservice.sns.DocumentSNSPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PolicyDocumentWriteServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private PdfGenerator pdfGenerator;

    @Mock
    private S3DocumentProvider documentProvider;

    @Mock
    private DocumentSNSPublisher snsPublisher;

    @InjectMocks
    private PolicyDocumentWriteService policyDocumentWriteService;

    @Test
    void shouldValidatePolicyDocumentCreationRequest() {
        // given
        PolicyDocumentRequest request = new PolicyDocumentRequest();

        // when
        PolicyDocumentRequest result = policyDocumentWriteService.validate(request);

        // then
        assertSame(request, result, "Validate should return the same request object");
    }

    @Test
    void shouldGeneratePolicyDocument() {
        // given
        PolicyDocumentRequest request = new PolicyDocumentRequest();
        GeneratedPdfHolder expectedPdf = new GeneratedPdfHolder("test.pdf", new byte[]{1, 2, 3});
        when(pdfGenerator.generatePdf(request)).thenReturn(expectedPdf);

        // when
        GeneratedPdfHolder result = policyDocumentWriteService.generate(request);

        // then
        assertSame(expectedPdf, result, "Generate should return the PDF from the generator");
        verify(pdfGenerator).generatePdf(request);
    }

    @Test
    void shouldStorePolicyDocument() {
        // given
        String fileName = UUID.randomUUID() + ".pdf";
        byte[] documentBytes = new byte[]{1, 2, 3};
        GeneratedPdfHolder pdfHolder = new GeneratedPdfHolder(fileName, documentBytes);

        // when
        SentDocumentMetadata result = policyDocumentWriteService.sendToStorage(pdfHolder);

        // then
        verify(documentProvider).store(documentBytes, fileName);
        assertEquals(fileName, result.fileName());
        assertEquals("policy", result.entityType());
    }

    @Test
    void shouldSavePolicyDocumentMetadata() {
        // given
        SentDocumentMetadata metadata = new SentDocumentMetadata(
                UUID.randomUUID(), "policy", "test.pdf");
        Document document = new Document();
        when(documentMapper.map(metadata)).thenReturn(document);

        // when
        SentDocumentMetadata result = policyDocumentWriteService.save(metadata);

        // then
        verify(documentMapper).map(metadata);
        verify(documentRepository).save(document);
        assertSame(metadata, result, "Save should return the same metadata object");
    }

    @Test
    void shouldProcessPolicyDocument() {
        // given
        PolicyDocumentRequest request = new PolicyDocumentRequest();
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + ".pdf";
        GeneratedPdfHolder pdfHolder = new GeneratedPdfHolder(fileName, new byte[]{1, 2, 3});
        Document document = new Document();

        // Mock dependencies
        when(pdfGenerator.generatePdf(request)).thenReturn(pdfHolder);
        when(documentMapper.map(any(SentDocumentMetadata.class))).thenReturn(document);
        when(snsPublisher.createTopicIfNoneExist("document-saved")).thenReturn("topic-arn");
        
        // Explicitly set the snsPublisher
        policyDocumentWriteService.setSnsPublisher(snsPublisher);

        // when
        SentDocumentMetadata result = policyDocumentWriteService.process(request);

        // then
        verify(pdfGenerator).generatePdf(request);
        verify(documentProvider).store(pdfHolder.documentAsBytes(), pdfHolder.fileName());
        verify(documentMapper).map(any(SentDocumentMetadata.class));
        verify(documentRepository).save(document);
        verify(snsPublisher).createTopicIfNoneExist("document-saved");
        verify(snsPublisher).publish(eq("topic-arn"), anyString());
        assertEquals("policy", result.entityType());
        assertEquals(fileName, result.fileName());
        assertEquals(uuid, result.id());
    }
}
