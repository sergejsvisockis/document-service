package io.github.sergejsvisockis.documentservice.api;

import com.sergejs.documentservice.api.model.DocumentResponse;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentResponseMapper {

    DocumentResponse mapMetadataToDocumentResponse(SentDocumentMetadata sentDocumentMetadata);

    List<DocumentResponse> mapDocumentsToDocumentResponses(List<Document> documents);
}
