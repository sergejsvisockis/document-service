package io.github.sergejsvisockis.documentservice.service;

import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "id", target = "documentId")
    @Mapping(source = "entityType", target = "entityType")
    @Mapping(source = "fileName", target = "fileName")
    Document map(SentDocumentMetadata metadata);
}
