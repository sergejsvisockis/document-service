package io.github.sergejsvisockis.documentservice.write.service;

import com.sergejs.documentservice.write.api.model.PolicyDocumentRequest;
import io.github.sergejsvisockis.documentservice.write.repository.DocumentMetadata;
import io.github.sergejsvisockis.documentservice.write.repository.DocumentWriteRepository;
import io.github.sergejsvisockis.documentservice.write.service.dto.SavedDocumentMetadata;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PolicyDocumentWriteService extends BaseDocumentWriteService<PolicyDocumentRequest> {

    private final DocumentWriteRepository documentWriteRepository;
    private final DocumentMapper documentMapper;

    public PolicyDocumentWriteService(DocumentWriteRepository documentWriteRepository,
                                      DocumentMapper documentMapper) {
        this.documentWriteRepository = documentWriteRepository;
        this.documentMapper = documentMapper;
    }

    @Override
    PolicyDocumentRequest validate(PolicyDocumentRequest request) {
        // TODO
        return request;
    }

    @Override
    Resource generate(PolicyDocumentRequest request) {
        // TODO: Call towards the external system. Mock has to be created.
        return new ByteArrayResource(new byte[0]);
    }

    @Override
    SavedDocumentMetadata sendToStorage(Resource request) {
        // TODO: An S3 client has to be created.
        return new SavedDocumentMetadata(UUID.randomUUID(), "policy", "policy.pdf");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    SavedDocumentMetadata writeMetadata(SavedDocumentMetadata request) {

        DocumentMetadata map = documentMapper.map(request);
        documentWriteRepository.save(map);

        return request;
    }
}
