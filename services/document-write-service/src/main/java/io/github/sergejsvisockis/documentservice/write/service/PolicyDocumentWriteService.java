package io.github.sergejsvisockis.documentservice.write.service;

import com.sergejs.documentservice.write.api.model.PolicyDocumentRequest;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import io.github.sergejsvisockis.documentservice.write.dynamodb.entity.DocumentMetadata;
import io.github.sergejsvisockis.documentservice.write.service.dto.SavedDocumentMetadata;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PolicyDocumentWriteService extends BaseDocumentWriteService<PolicyDocumentRequest> {

    private final DynamoDbTemplate documentWriteRepository;
    private final DocumentMapper documentMapper;

    public PolicyDocumentWriteService(DynamoDbTemplate documentWriteRepository,
                                      DocumentMapper documentMapper) {
        this.documentWriteRepository = documentWriteRepository;
        this.documentMapper = documentMapper;
    }

    @Override
    public PolicyDocumentRequest validate(PolicyDocumentRequest request) {
        // TODO
        return request;
    }

    @Override
    public Resource generate(PolicyDocumentRequest request) {
        // TODO: Call towards the external system. Mock has to be created.
        return new ByteArrayResource(new byte[0]);
    }

    @Override
    public SavedDocumentMetadata sendToStorage(Resource request) {
        // TODO: An S3 client has to be created.
        return new SavedDocumentMetadata(UUID.randomUUID(), "policy", "policy.pdf");
    }

    @Override
    public SavedDocumentMetadata writeMetadata(SavedDocumentMetadata request) {

        DocumentMetadata map = documentMapper.map(request);
        documentWriteRepository.save(map);

        return request;
    }
}
