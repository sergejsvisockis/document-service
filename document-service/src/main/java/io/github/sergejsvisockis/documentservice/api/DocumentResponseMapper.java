package io.github.sergejsvisockis.documentservice.api;

import com.sergejs.documentservice.api.model.DocumentResponse;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentResponseMapper {

    DocumentResponse mapMetadataToDocumentResponse(SentDocumentMetadata sentDocumentMetadata);

    @Mapping(source = "documentId", target = "id")
    DocumentResponse mapDocumentToDocumentResponse(Document document);

    List<DocumentResponse> mapDocumentsToDocumentResponses(List<Document> documents);
}
