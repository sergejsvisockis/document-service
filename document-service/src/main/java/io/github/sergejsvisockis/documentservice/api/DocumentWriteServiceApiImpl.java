package io.github.sergejsvisockis.documentservice.api;

import com.sergejs.documentservice.api.DocumentWriteServiceApi;
import com.sergejs.documentservice.api.model.ClaimDocumentRequest;
import com.sergejs.documentservice.api.model.DocumentResponse;
import com.sergejs.documentservice.api.model.InvoiceDocumentRequest;
import com.sergejs.documentservice.api.model.PolicyDocumentRequest;
import io.github.sergejsvisockis.documentservice.service.ClaimDocumentWriteService;
import io.github.sergejsvisockis.documentservice.service.InvoiceDocumentWriteService;
import io.github.sergejsvisockis.documentservice.service.PolicyDocumentWriteService;
import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/document-service/write")
@RequiredArgsConstructor
public class DocumentWriteServiceApiImpl implements DocumentWriteServiceApi {

    private final DocumentResponseMapper mapper;
    private final PolicyDocumentWriteService policyDocumentWriteService;
    private final ClaimDocumentWriteService claimDocumentWriteService;
    private final InvoiceDocumentWriteService invoiceDocumentWriteService;

    @Override
    public ResponseEntity<DocumentResponse> writeClaimDocument(ClaimDocumentRequest claimDocumentRequest) {
        return process(claimDocumentWriteService.process(claimDocumentRequest));
    }

    @Override
    public ResponseEntity<DocumentResponse> writeInvoiceDocument(InvoiceDocumentRequest invoiceDocumentRequest) {
        return process(invoiceDocumentWriteService.process(invoiceDocumentRequest));
    }

    @Override
    public ResponseEntity<DocumentResponse> writePolicyDocument(PolicyDocumentRequest policyDocumentRequest) {
        return process(policyDocumentWriteService.process(policyDocumentRequest));
    }

    private ResponseEntity<DocumentResponse> process(SentDocumentMetadata policyDocumentWriteService) {
        DocumentResponse mappedDocument = mapper.mapMetadataToDocumentResponse(policyDocumentWriteService);
        return ResponseEntity.ok(mappedDocument);
    }
}
