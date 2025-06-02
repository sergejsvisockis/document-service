package io.github.sergejsvisockis.documentservice.provider;

/**
 * This is the base document storage provider which is supposed to provide a single contract
 * for all the subsequent implementations.
 * For example there might be an AWS S3 implementation, SFTP implementation, some 3rd party REST webservice.
 *
 * @param <T> the return type of provider
 * @param <P> the document to be saved.
 */
public interface DocumentProvider<T, P> {

    /**
     * Get document by the filename.
     *
     * @param fileName the filename
     * @return the document. The format is strictly defined by the client of that particular interface.
     */
    T getDocument(String fileName);

    /**
     * Store the document on the storage.
     *
     * @param document document content.
     * @param fileName the resulting file name.
     */
    void store(P document, String fileName);
}
