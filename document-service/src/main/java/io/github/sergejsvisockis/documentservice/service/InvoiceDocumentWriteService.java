package io.github.sergejsvisockis.documentservice.service;

import com.sergejs.documentservice.api.model.InvoiceDocumentRequest;
import io.github.sergejsvisockis.documentservice.pdf.GeneratedPdfHolder;
import io.github.sergejsvisockis.documentservice.pdf.PdfGenerator;
import io.github.sergejsvisockis.documentservice.provider.S3DocumentProvider;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class InvoiceDocumentWriteService extends BaseDocumentWriteService<InvoiceDocumentRequest, GeneratedPdfHolder> {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final PdfGenerator pdfGenerator;
    private final S3DocumentProvider documentProvider;

    @Override
    public InvoiceDocumentRequest validate(InvoiceDocumentRequest request) {
        return request;
    }

    @Override
    public GeneratedPdfHolder generate(InvoiceDocumentRequest request) {
        return pdfGenerator.generatePdf(request);
    }

    @Override
    @SneakyThrows
    public SentDocumentMetadata sendToStorage(GeneratedPdfHolder request) {
        documentProvider.store(request.documentAsBytes(), request.fileName());
        return constructSentDocumentResponse(request.fileName(), ".pdf", "invoice");
    }

    @Override
    public SentDocumentMetadata save(SentDocumentMetadata request) {

        Document map = documentMapper.map(request);
        documentRepository.save(map);

        return request;
    }
}
