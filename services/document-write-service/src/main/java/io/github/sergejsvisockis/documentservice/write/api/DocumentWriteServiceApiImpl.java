package io.github.sergejsvisockis.documentservice.write.api;

import com.sergejs.documentservice.write.api.DocumentWriteServiceApi;
import com.sergejs.documentservice.write.api.model.ClaimDocumentRequest;
import com.sergejs.documentservice.write.api.model.DocumentResponse;
import com.sergejs.documentservice.write.api.model.InvoiceDocumentRequest;
import com.sergejs.documentservice.write.api.model.PolicyDocumentRequest;
import io.github.sergejsvisockis.documentservice.write.service.ClaimDocumentWriteService;
import io.github.sergejsvisockis.documentservice.write.service.InvoiceDocumentWriteService;
import io.github.sergejsvisockis.documentservice.write.service.PolicyDocumentWriteService;
import io.github.sergejsvisockis.documentservice.write.service.dto.SavedDocumentMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/document-service/write")
public class DocumentWriteServiceApiImpl implements DocumentWriteServiceApi {

    private final DocumentResponseMapper mapper;
    private final PolicyDocumentWriteService policyDocumentWriteService;
    private final ClaimDocumentWriteService claimDocumentWriteService;
    private final InvoiceDocumentWriteService invoiceDocumentWriteService;

    public DocumentWriteServiceApiImpl(DocumentResponseMapper mapper,
                                       PolicyDocumentWriteService policyDocumentWriteService,
                                       ClaimDocumentWriteService claimDocumentWriteService,
                                       InvoiceDocumentWriteService invoiceDocumentWriteService) {
        this.mapper = mapper;
        this.policyDocumentWriteService = policyDocumentWriteService;
        this.claimDocumentWriteService = claimDocumentWriteService;
        this.invoiceDocumentWriteService = invoiceDocumentWriteService;
    }

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

    private ResponseEntity<DocumentResponse> process(SavedDocumentMetadata policyDocumentWriteService) {
        DocumentResponse mappedDocument = mapper.mapMetadataToDocumentResponse(policyDocumentWriteService);
        return ResponseEntity.ok(mappedDocument);
    }
}
