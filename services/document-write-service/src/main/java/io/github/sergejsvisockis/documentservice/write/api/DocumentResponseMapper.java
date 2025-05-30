package io.github.sergejsvisockis.documentservice.write.api;

import com.sergejs.documentservice.write.api.model.DocumentResponse;
import io.github.sergejsvisockis.documentservice.write.service.dto.SavedDocumentMetadata;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentResponseMapper {

    DocumentResponse mapMetadataToDocumentResponse(SavedDocumentMetadata savedDocumentMetadata);

}
