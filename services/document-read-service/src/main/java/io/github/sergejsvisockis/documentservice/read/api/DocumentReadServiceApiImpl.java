package io.github.sergejsvisockis.documentservice.read.api;

import com.sergejs.documentservice.read.api.DocumentReadServiceApi;
import com.sergejs.documentservice.read.api.model.DocumentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/v1/document-service/read")
public class DocumentReadServiceApiImpl implements DocumentReadServiceApi {

    @Override
    public ResponseEntity<DocumentResponse> readAllDocuments(String documentType) {
        return ResponseEntity.ok(new DocumentResponse());
    }

    @Override
    public ResponseEntity<Object> readSingleDocument(UUID documentId) {
        return null;
    }
}
