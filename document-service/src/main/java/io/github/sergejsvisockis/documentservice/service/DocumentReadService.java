package io.github.sergejsvisockis.documentservice.service;

import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.List;

@Service
public class DocumentReadService {

    private final S3Client s3Client;
    private final DocumentRepository documentRepository;

    public DocumentReadService(S3Client s3Client, DocumentRepository documentRepository) {
        this.s3Client = s3Client;
        this.documentRepository = documentRepository;
    }

    public List<Document> getDocumentMetadata(String type) {
        return documentRepository.findAllByType(type);
    }

    public ResponseInputStream<GetObjectResponse> getDocument(String fileName) {
        return s3Client.getObject(GetObjectRequest.builder().bucket("insurtechstorage")
                .key(fileName)
                .build());
    }
}
