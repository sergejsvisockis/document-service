package io.github.sergejsvisockis.documentservice.docgen;

/**
 * The contract for any documentation generation implementation.
 * No matter what document that would be every implementation has to implement this interface.
 *
 * @param <T> the request type.
 * @param <V> the response type.
 */
public interface DocumentGenerator<T, V> {

    /**
     * Supposed to execute the main document generation logic.
     *
     * @param request data that document has to contain.
     * @return acknowledgement, usually basic metadata like file name.
     */
    V generate(T request);

}
