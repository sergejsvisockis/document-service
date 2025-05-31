package io.github.sergejsvisockis.documentservice.service.dto;

import java.util.UUID;

public final class SentDocumentMetadata {

    private final UUID id;
    private final String documentType;
    private final String fileName;

    public SentDocumentMetadata(UUID id, String documentType, String fileName) {
        this.id = id;
        this.documentType = documentType;
        this.fileName = fileName;
    }

    public UUID getId() {
        return id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getFileName() {
        return fileName;
    }
}
