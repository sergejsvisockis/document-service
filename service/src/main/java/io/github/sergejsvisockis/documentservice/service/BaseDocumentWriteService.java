package io.github.sergejsvisockis.documentservice.service;

import io.github.sergejsvisockis.documentservice.service.dto.SentDocumentMetadata;
import io.github.sergejsvisockis.documentservice.sns.DocumentSNSPublisher;
import io.github.sergejsvisockis.documentservice.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * This class is a base document processing implementation.
 * Each method is to be implemented by each entity since that is possible
 * that some of them might be saved into the AWS S3, some into the SFT and some others into one another storage.
 * The same comes towards the document type some entities might require PDF while others DOCX for example.
 * This is why an implementation has to be as generic as possible.
 *
 * @param <T> the request type coming out of the REST service.
 * @param <G> the response type of the object constructed after the document being generated.
 */
public abstract class BaseDocumentWriteService<T, G> {

    private DocumentSNSPublisher snsPublisher;

    public SentDocumentMetadata process(T request) {
        T validatedRequest = validate(request);

        G document = generate(validatedRequest);

        SentDocumentMetadata sentDocumentMetadata = sendToStorage(document);

        SentDocumentMetadata savedDocument = save(sentDocumentMetadata);

        snsPublisher.publish(JsonUtil.toJson(savedDocument));

        return savedDocument;
    }

    /**
     * Validates document request body, which comes from the REST service.
     *
     * @param request the request body
     * @return validated request body
     */
    public abstract T validate(T request);

    /**
     * Supposed to generate a document which is supposed to be saved into some sort of storage later on.
     *
     * @param request the request containing the document metadata to insert into the predefined template.
     *                Generator could be any 3rd party subsystem.
     * @return generated document metadata.
     */
    public abstract G generate(T request);

    /**
     * Sent the PDF document into the storage.
     * Any storage could be an AWS S3, SFTP, etc.
     *
     * @param request the message request to send to the storage.
     * @return {@link SentDocumentMetadata} saved document metadata
     */
    public abstract SentDocumentMetadata sendToStorage(G request);

    /**
     * Supposed to write saved document metadata.
     *
     * @param request saved document request metadata.
     * @return {@link SentDocumentMetadata} saved document metadata.
     */
    public abstract SentDocumentMetadata save(SentDocumentMetadata request);


    SentDocumentMetadata constructSentDocumentResponse(String fileName, String documentType, String entityType) {
        return new SentDocumentMetadata(
                UUID.fromString(fileName.replace(documentType, "")),
                entityType,
                fileName
        );
    }

    @Autowired
    public void setSnsPublisher(DocumentSNSPublisher snsPublisher) {
        this.snsPublisher = snsPublisher;
    }
}
