package io.github.sergejsvisockis.documentservice.write.service;

import io.github.sergejsvisockis.documentservice.write.repository.DocumentMetadata;
import io.github.sergejsvisockis.documentservice.write.service.dto.SavedDocumentMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface DocumentMapper {

    @Mapping(ignore = true, target = "id")
    @Mapping(source = "id", target = "documentId")
    @Mapping(source = "documentType", target = "documentType")
    @Mapping(source = "fileName", target = "fileName")
    DocumentMetadata map(SavedDocumentMetadata metadata);
}
