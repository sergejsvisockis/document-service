package io.github.sergejsvisockis.documentservice.service;

import io.github.sergejsvisockis.documentservice.api.model.PolicyDocumentRequest;
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
public class PolicyDocumentWriteService extends BaseDocumentWriteService<PolicyDocumentRequest, GeneratedPdfHolder> {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final DocumentGenerator<PolicyDocumentRequest, GeneratedPdfHolder> pdfDocumentGenerator;
    private final DocumentProvider<ResponseInputStream<GetObjectResponse>, byte[]> documentProvider;

    @Override
    public PolicyDocumentRequest validate(PolicyDocumentRequest request) {
        return request;
    }

    @Override
    public GeneratedPdfHolder generate(PolicyDocumentRequest request) {
        return pdfDocumentGenerator.generate(request);
    }

    @Override
    public SentDocumentMetadata sendToStorage(GeneratedPdfHolder request) {
        documentProvider.store(request.documentAsBytes(), request.fileName());
        return constructSentDocumentResponse(request.fileName(), ".pdf", "policy");
    }

    @Override
    public SentDocumentMetadata save(SentDocumentMetadata request) {

        Document map = documentMapper.map(request);
        documentRepository.save(map);

        return request;
    }
}
