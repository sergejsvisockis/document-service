package io.github.sergejsvisockis.documentservice.service;

import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentReadService {

    private final DocumentRepository documentRepository;

    public DocumentReadService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> findAllByType(String type) {
        return documentRepository.findAllByType(type);
    }
}
