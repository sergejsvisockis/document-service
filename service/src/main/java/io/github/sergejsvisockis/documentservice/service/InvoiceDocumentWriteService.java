package io.github.sergejsvisockis.documentservice.service;

import io.github.sergejsvisockis.documentservice.api.model.InvoiceDocumentRequest;
import io.github.sergejsvisockis.documentservice.docgen.DocumentGenerator;
import io.github.sergejsvisockis.documentservice.docgen.pdf.GeneratedPdfHolder;
import io.github.sergejsvisockis.documentservice.provider.DocumentProvider;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@Service
@RequiredArgsConstructor
public class InvoiceDocumentWriteService extends BaseDocumentWriteService<InvoiceDocumentRequest, GeneratedPdfHolder> {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final DocumentGenerator<InvoiceDocumentRequest, GeneratedPdfHolder> pdfDocumentGenerator;
    private final DocumentProvider<ResponseInputStream<GetObjectResponse>, byte[]> documentProvider;

    @Override
    public InvoiceDocumentRequest validate(InvoiceDocumentRequest request) {
        return request;
    }

    @Override
    public GeneratedPdfHolder generate(InvoiceDocumentRequest request) {
        return pdfDocumentGenerator.generate(request);
    }

    @Override
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
