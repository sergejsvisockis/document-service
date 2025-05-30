package io.github.sergejsvisockis.documentservice.write.api;

import com.sergejs.documentservice.write.api.DocumentWriteServiceApi;
import com.sergejs.documentservice.write.api.model.ClaimDocumentRequest;
import com.sergejs.documentservice.write.api.model.DocumentResponse;
import com.sergejs.documentservice.write.api.model.InvoiceDocumentRequest;
import com.sergejs.documentservice.write.api.model.PolicyDocumentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/document-service/write")
public class DocumentWriteServiceApiImpl implements DocumentWriteServiceApi {

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
