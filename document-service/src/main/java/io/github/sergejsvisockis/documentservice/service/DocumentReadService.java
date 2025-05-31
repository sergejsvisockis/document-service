package io.github.sergejsvisockis.documentservice.service;

import io.awspring.cloud.s3.S3Template;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentReadService {

    private final S3Template s3Template;
    private final DocumentRepository documentRepository;

    public DocumentReadService(S3Template s3Template, DocumentRepository documentRepository) {
        this.s3Template = s3Template;
        this.documentRepository = documentRepository;
    }

    public List<Document> getDocumentMetadata(String type) {
        return documentRepository.findAllByType(type);
    }

    public Object getDocument(UUID documentId) {
        try {
            return s3Template.download("insurtechstorage", documentId.toString()).getInputStream();
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read document from the S3 bucket", e);
        }
    }
}
