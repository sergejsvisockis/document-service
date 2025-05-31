package io.github.sergejsvisockis.documentservice.storage;

/**
 * This is the base document storage manager which is supposed to provide a single contract
 * for all the subsequent implementations.
 * For example there might be an AWS S3 implementation, SFTP implementation, some 3rd party REST webservice.
 *
 * @param <T> the return type of provider
 */
public interface DocumentStorageManager<T> {

    /**
     * Get document by the filename.
     *
     * @param fileName the filename
     * @return the document. The format is strictly defined by the client of that particular interface.
     */
    T getDocument(String fileName);

    // TODO: USe an S3 as the base
}
