package io.github.sergejsvisockis.documentservice.service;

import com.sergejs.documentservice.api.model.ClaimDocumentRequest;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClaimDocumentWriteService extends BaseDocumentWriteService<ClaimDocumentRequest> {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public ClaimDocumentWriteService(DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    @Override
    public ClaimDocumentRequest validate(ClaimDocumentRequest request) {
        // TODO
        return request;
    }

    @Override
    public Resource generate(ClaimDocumentRequest request) {
        // TODO: Call towards the external system. Mock has to be created.
        return new ByteArrayResource(new byte[0]);
    }

    @Override
    public SentDocumentMetadata sendToStorage(Resource request) {
        // TODO: An S3 client has to be created.
        return new SentDocumentMetadata(UUID.randomUUID(), "claim", "claim.pdf");
    }

    @Override
    public SentDocumentMetadata save(SentDocumentMetadata request) {

        Document map = documentMapper.map(request);
        documentRepository.save(map);

        return request;
    }
}
