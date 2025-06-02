package io.github.sergejsvisockis.documentservice.service;

import io.github.sergejsvisockis.documentservice.api.model.InvoiceDocumentRequest;
import io.github.sergejsvisockis.documentservice.docgen.DocumentGenerator;
import io.github.sergejsvisockis.documentservice.docgen.pdf.GeneratedPdfHolder;
import io.github.sergejsvisockis.documentservice.provider.DocumentProvider;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import io.github.sergejsvisockis.documentservice.sns.DocumentSNSPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoiceDocumentWriteServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private DocumentGenerator<InvoiceDocumentRequest, GeneratedPdfHolder> pdfDocumentGenerator;

    @Mock
    private DocumentProvider<ResponseInputStream<GetObjectResponse>, byte[]> documentProvider;

    @Mock
    private DocumentSNSPublisher snsPublisher;

    @InjectMocks
    private InvoiceDocumentWriteService invoiceDocumentWriteService;

    @Test
    void shouldValidateInvoiceDocumentCreationRequest() {
        // given
        InvoiceDocumentRequest request = new InvoiceDocumentRequest();

        // when
        InvoiceDocumentRequest result = invoiceDocumentWriteService.validate(request);

        // then
        assertSame(request, result, "Validate should return the same request object");
    }

    @Test
    void shouldGenerateInvoiceDocument() {
        // given
        InvoiceDocumentRequest request = new InvoiceDocumentRequest();
        GeneratedPdfHolder expectedPdf = new GeneratedPdfHolder("test.pdf", new byte[]{1, 2, 3});
        when(pdfDocumentGenerator.generate(request)).thenReturn(expectedPdf);

        // when
        GeneratedPdfHolder result = invoiceDocumentWriteService.generate(request);

        // then
        assertSame(expectedPdf, result, "Generate should return the PDF from the generator");
        verify(pdfDocumentGenerator).generate(request);
    }

    @Test
    void shouldStoreInvoiceDocument() {
        // given
        String fileName = UUID.randomUUID() + ".pdf";
        byte[] documentBytes = new byte[]{1, 2, 3};
        GeneratedPdfHolder pdfHolder = new GeneratedPdfHolder(fileName, documentBytes);

        // when
        SentDocumentMetadata result = invoiceDocumentWriteService.sendToStorage(pdfHolder);

        // then
        verify(documentProvider).store(documentBytes, fileName);
        assertEquals(fileName, result.fileName());
        assertEquals("invoice", result.entityType());
    }

    @Test
    void shouldSaveInvoiceDocumentMetadata() {
        // given
        SentDocumentMetadata metadata = new SentDocumentMetadata(
                UUID.randomUUID(), "invoice", "test.pdf");
        Document document = new Document();
        when(documentMapper.map(metadata)).thenReturn(document);

        // when
        SentDocumentMetadata result = invoiceDocumentWriteService.save(metadata);

        // then
        verify(documentMapper).map(metadata);
        verify(documentRepository).save(document);
        assertSame(metadata, result, "Save should return the same metadata object");
    }

    @Test
    void shouldProcessInvoiceDocument() {
        // given
        InvoiceDocumentRequest request = new InvoiceDocumentRequest();
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + ".pdf";
        GeneratedPdfHolder pdfHolder = new GeneratedPdfHolder(fileName, new byte[]{1, 2, 3});
        Document document = new Document();

        // Mock dependencies
        when(pdfDocumentGenerator.generate(request)).thenReturn(pdfHolder);
        when(documentMapper.map(any(SentDocumentMetadata.class))).thenReturn(document);

        // Explicitly set the snsPublisher
        invoiceDocumentWriteService.setSnsPublisher(snsPublisher);

        // when
        SentDocumentMetadata result = invoiceDocumentWriteService.process(request);

        // then
        verify(pdfDocumentGenerator).generate(request);
        verify(documentProvider).store(pdfHolder.documentAsBytes(), pdfHolder.fileName());
        verify(documentMapper).map(any(SentDocumentMetadata.class));
        verify(documentRepository).save(document);
        verify(snsPublisher).publish(anyString());
        assertEquals("invoice", result.entityType());
        assertEquals(fileName, result.fileName());
        assertEquals(uuid, result.id());
    }
}
