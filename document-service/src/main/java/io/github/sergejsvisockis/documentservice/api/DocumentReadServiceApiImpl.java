package io.github.sergejsvisockis.documentservice.api;

import com.sergejs.documentservice.api.DocumentReadServiceApi;
import com.sergejs.documentservice.api.model.DocumentReadResponse;
import com.sergejs.documentservice.api.model.DocumentResponse;
import io.github.sergejsvisockis.documentservice.repository.Document;
import io.github.sergejsvisockis.documentservice.service.DocumentReadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/document-service/read")
public class DocumentReadServiceApiImpl implements DocumentReadServiceApi {

    private final DocumentReadService documentReadService;
    private final DocumentResponseMapper documentResponseMapper;

    public DocumentReadServiceApiImpl(DocumentReadService documentReadService,
                                      DocumentResponseMapper documentResponseMapper) {
        this.documentReadService = documentReadService;
        this.documentResponseMapper = documentResponseMapper;
    }

    @Override
    public ResponseEntity<DocumentReadResponse> getDocumentMetadata(String documentType) {
        List<Document> allDocuments = documentReadService.getDocumentMetadata(documentType);

        List<DocumentResponse> documents = documentResponseMapper.mapDocumentsToDocumentResponses(allDocuments);

        return ResponseEntity.ok(new DocumentReadResponse()
                .documents(documents));
    }

    @Override
    public ResponseEntity<Object> getDocument(UUID documentId) {
        return ResponseEntity.ok(documentReadService.getDocument(documentId));
    }
}
