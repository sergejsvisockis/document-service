package io.github.sergejsvisockis.documentservice.write.service;

import com.sergejs.documentservice.write.api.model.InvoiceDocumentRequest;
import io.github.sergejsvisockis.documentservice.write.repository.Document;
import io.github.sergejsvisockis.documentservice.write.repository.DocumentRepository;
import io.github.sergejsvisockis.documentservice.write.service.dto.SavedDocumentMetadata;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvoiceDocumentWriteService extends BaseDocumentWriteService<InvoiceDocumentRequest> {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public InvoiceDocumentWriteService(DocumentRepository documentRepository,
                                       DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    @Override
    public InvoiceDocumentRequest validate(InvoiceDocumentRequest request) {
        // TODO
        return request;
    }

    @Override
    public Resource generate(InvoiceDocumentRequest request) {
        // TODO: Call towards the external system. Mock has to be created.
        return new ByteArrayResource(new byte[0]);
    }

    @Override
    public SavedDocumentMetadata sendToStorage(Resource request) {
        // TODO: An S3 client has to be created.
        return new SavedDocumentMetadata(UUID.randomUUID(), "invoice", "invoice.pdf");
    }

    @Override
    public SavedDocumentMetadata writeMetadata(SavedDocumentMetadata request) {

        Document map = documentMapper.map(request);
        documentRepository.save(map);

        return request;
    }
}
