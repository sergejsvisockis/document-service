package io.github.sergejsvisockis.documentservice.api;

import io.github.sergejsvisockis.documentservice.api.model.DocumentReadResponse;
import io.github.sergejsvisockis.documentservice.api.model.DocumentResponse;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.service.DocumentReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class DocumentReadServiceApiImpl implements DocumentReadServiceApi {

    private final DocumentReadService documentReadService;
    private final DocumentResponseMapper documentResponseMapper;

    @Override
    public ResponseEntity<DocumentReadResponse> getDocumentMetadata(String documentType) {
        List<Document> allDocuments = documentReadService.getDocumentMetadata(documentType);

        List<DocumentResponse> documents = documentResponseMapper.mapDocumentsToDocumentResponses(allDocuments);

        return ResponseEntity.ok(new DocumentReadResponse()
                .documents(documents));
    }

    @Override
    public ResponseEntity<Object> getDocument(UUID documentId) {
        String fileName = documentId.toString() + ".pdf";

        ResponseInputStream<GetObjectResponse> s3Object = documentReadService.getDocument(fileName);

        InputStreamResource resource = new InputStreamResource(s3Object);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
