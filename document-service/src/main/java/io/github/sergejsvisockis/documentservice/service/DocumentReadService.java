package io.github.sergejsvisockis.documentservice.service;

import io.github.sergejsvisockis.documentservice.provider.DocumentProvider;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.List;

@Service
public class DocumentReadService {

    private final DocumentProvider<ResponseInputStream<GetObjectResponse>> storageProvider;
    private final DocumentRepository documentRepository;

    public DocumentReadService(DocumentProvider<ResponseInputStream<GetObjectResponse>> storageProvider,
                               DocumentRepository documentRepository) {
        this.storageProvider = storageProvider;
        this.documentRepository = documentRepository;
    }

    public List<Document> getDocumentMetadata(String type) {
        return documentRepository.findAllByType(type);
    }

    public ResponseInputStream<GetObjectResponse> getDocument(String fileName) {
        return storageProvider.getDocument(fileName);
    }
}
