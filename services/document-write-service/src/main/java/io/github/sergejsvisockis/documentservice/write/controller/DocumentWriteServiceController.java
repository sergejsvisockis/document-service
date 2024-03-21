package io.github.sergejsvisockis.documentservice.write.controller;

import io.github.sergejsvisockis.documentservice.api.service.v1.DocumentWriteServiceApi;
import io.github.sergejsvisockis.documentservice.api.service.v1.model.ClaimDocumentRequest;
import io.github.sergejsvisockis.documentservice.api.service.v1.model.DocumentResponse;
import io.github.sergejsvisockis.documentservice.api.service.v1.model.InvoiceDocumentRequest;
import io.github.sergejsvisockis.documentservice.api.service.v1.model.PolicyDocumentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DocumentWriteServiceController implements DocumentWriteServiceApi {

    @Override
    public ResponseEntity<DocumentResponse> writeClaimDocument(ClaimDocumentRequest claimDocumentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<DocumentResponse> writeInvoiceDocument(InvoiceDocumentRequest invoiceDocumentRequest) {
        return null;
    }

    @Override
    public ResponseEntity<DocumentResponse> writePolicyDocument(PolicyDocumentRequest policyDocumentRequest) {
        return null;
    }

}
