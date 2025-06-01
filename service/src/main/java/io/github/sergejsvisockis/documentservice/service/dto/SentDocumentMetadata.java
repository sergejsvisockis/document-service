package io.github.sergejsvisockis.documentservice.service.dto;

import java.util.UUID;

public record SentDocumentMetadata(UUID id, String entityType, String fileName) {

}
