package io.github.sergejsvisockis.documentservice.service;

import com.sergejs.documentservice.api.model.ClaimDocumentRequest;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClaimDocumentWriteServiceTest {

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
    private ClaimDocumentWriteService claimDocumentWriteService;

    @Test
    void shouldValidateClaimDocumentRequest() {
        // given
        ClaimDocumentRequest request = new ClaimDocumentRequest();

        // when
        ClaimDocumentRequest result = claimDocumentWriteService.validate(request);

        // then
        assertSame(request, result, "Validate should return the same request object");
    }

    @Test
    void shouldGenerateClaimDocument() {
        // given
        ClaimDocumentRequest request = new ClaimDocumentRequest();
        GeneratedPdfHolder expectedPdf = new GeneratedPdfHolder("test.pdf", new byte[]{1, 2, 3});
        when(pdfGenerator.generatePdf(request)).thenReturn(expectedPdf);

        // when
        GeneratedPdfHolder result = claimDocumentWriteService.generate(request);

        // then
        assertSame(expectedPdf, result, "Generate should return the PDF from the generator");
        verify(pdfGenerator).generatePdf(request);
    }

    @Test
    void shouldSendClaimDocumentToStorage() {
        // given
        String fileName = UUID.randomUUID() + ".pdf";
        byte[] documentBytes = new byte[]{1, 2, 3};
        GeneratedPdfHolder pdfHolder = new GeneratedPdfHolder(fileName, documentBytes);

        // when
        SentDocumentMetadata result = claimDocumentWriteService.sendToStorage(pdfHolder);

        // then
        verify(documentProvider).store(documentBytes, fileName);
        assertEquals(fileName, result.fileName());
        assertEquals("claim", result.entityType());
    }

    @Test
    void shouldSaveClaimDocument() {
        // given
        SentDocumentMetadata metadata = new SentDocumentMetadata(
                UUID.randomUUID(), "claim", "test.pdf");
        Document document = new Document();
        when(documentMapper.map(metadata)).thenReturn(document);

        // when
        SentDocumentMetadata result = claimDocumentWriteService.save(metadata);

        // then
        verify(documentMapper).map(metadata);
        verify(documentRepository).save(document);
        assertSame(metadata, result, "Save should return the same metadata object");
    }

    @Test
    void shouldProcessClaimDocument() {
        // given
        ClaimDocumentRequest request = new ClaimDocumentRequest();
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + ".pdf";
        GeneratedPdfHolder pdfHolder = new GeneratedPdfHolder(fileName, new byte[]{1, 2, 3});
        Document document = new Document();

        // Mock dependencies
        when(pdfGenerator.generatePdf(request)).thenReturn(pdfHolder);
        when(documentMapper.map(any(SentDocumentMetadata.class))).thenReturn(document);

        // Explicitly set the snsPublisher
        claimDocumentWriteService.setSnsPublisher(snsPublisher);

        // when
        SentDocumentMetadata result = claimDocumentWriteService.process(request);

        // then
        verify(pdfGenerator).generatePdf(request);
        verify(documentProvider).store(pdfHolder.documentAsBytes(), pdfHolder.fileName());
        verify(documentMapper).map(any(SentDocumentMetadata.class));
        verify(documentRepository).save(document);
        verify(snsPublisher).publish(anyString());
        assertEquals("claim", result.entityType());
        assertEquals(fileName, result.fileName());
        assertEquals(uuid, result.id());
    }
}
