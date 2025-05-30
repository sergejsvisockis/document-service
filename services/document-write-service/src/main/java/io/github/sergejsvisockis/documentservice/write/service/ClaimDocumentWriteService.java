package io.github.sergejsvisockis.documentservice.write.service;

import com.sergejs.documentservice.write.api.model.ClaimDocumentRequest;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import io.github.sergejsvisockis.documentservice.write.dynamodb.entity.DocumentMetadata;
import io.github.sergejsvisockis.documentservice.write.service.dto.SavedDocumentMetadata;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClaimDocumentWriteService extends BaseDocumentWriteService<ClaimDocumentRequest> {

    private final DynamoDbTemplate dynamoDbTemplate;
    private final DocumentMapper documentMapper;

    public ClaimDocumentWriteService(DynamoDbTemplate dynamoDbTemplate,
                                     DocumentMapper documentMapper) {
        this.dynamoDbTemplate = dynamoDbTemplate;
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
    public SavedDocumentMetadata writeMetadata(SavedDocumentMetadata request) {

        DocumentMetadata map = documentMapper.map(request);
        dynamoDbTemplate.save(map);

        return request;
    }
}
