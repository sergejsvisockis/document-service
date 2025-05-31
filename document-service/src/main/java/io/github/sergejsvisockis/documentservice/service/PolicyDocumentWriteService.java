package io.github.sergejsvisockis.documentservice.service;

import com.sergejs.documentservice.api.model.PolicyDocumentRequest;
import io.github.sergejsvisockis.documentservice.pdf.GeneratedPdfHolder;
import io.github.sergejsvisockis.documentservice.pdf.PdfGenerator;
import io.github.sergejsvisockis.documentservice.provider.S3DocumentProvider;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PolicyDocumentWriteService extends BaseDocumentWriteService<PolicyDocumentRequest, GeneratedPdfHolder> {

    private final PdfGenerator pdfGenerator;
    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final S3DocumentProvider documentProvider;

    @Override
    public PolicyDocumentRequest validate(PolicyDocumentRequest request) {
        // TODO
        return request;
    }

    @Override
    public GeneratedPdfHolder generate(PolicyDocumentRequest request) {
        return pdfGenerator.generatePdf(request);
    }

    @Override
    public SentDocumentMetadata sendToStorage(GeneratedPdfHolder request) {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            request.document().save(outputStream);

            documentProvider.store(outputStream, request.fileName());

            return new SentDocumentMetadata(
                    UUID.fromString(request.fileName().replace(".pdf", "")),
                    "policy",
                    request.fileName()
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public SentDocumentMetadata save(SentDocumentMetadata request) {

        Document map = documentMapper.map(request);
        documentRepository.save(map);

        return request;
    }
}
