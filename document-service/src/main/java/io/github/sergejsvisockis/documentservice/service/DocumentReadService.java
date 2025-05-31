package io.github.sergejsvisockis.documentservice.service;

import io.github.sergejsvisockis.documentservice.provider.S3DocumentProvider;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentReadService {

    private final S3DocumentProvider documentProvider;
    private final DocumentRepository documentRepository;

    public List<Document> getDocumentMetadata(String type) {
        return documentRepository.findAllByType(type);
    }

    public ResponseInputStream<GetObjectResponse> getDocument(String fileName) {
        return documentProvider.getDocument(fileName);
    }
}
