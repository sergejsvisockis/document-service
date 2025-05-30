package io.github.sergejsvisockis.documentservice.write.service;

import com.sergejs.documentservice.write.api.model.ClaimDocumentRequest;
import io.github.sergejsvisockis.documentservice.write.repository.DocumentMetadata;
import io.github.sergejsvisockis.documentservice.write.repository.DocumentWriteRepository;
import io.github.sergejsvisockis.documentservice.write.service.dto.SavedDocumentMetadata;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ClaimDocumentWriteService extends BaseDocumentWriteService<ClaimDocumentRequest> {

    private final DocumentWriteRepository documentWriteRepository;
    private final DocumentMapper documentMapper;

    public ClaimDocumentWriteService(DocumentWriteRepository documentWriteRepository,
                                     DocumentMapper documentMapper) {
        this.documentWriteRepository = documentWriteRepository;
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
    public SavedDocumentMetadata sendToStorage(Resource request) {
        // TODO: An S3 client has to be created.
        return new SavedDocumentMetadata(UUID.randomUUID(), "claim", "claim.pdf");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SavedDocumentMetadata writeMetadata(SavedDocumentMetadata request) {

        DocumentMetadata map = documentMapper.map(request);
        documentWriteRepository.save(map);

        return request;
    }
}
