package com.vluee.cloud.searchbuilder.core.document.exception;

public class SearchableDocumentException extends RuntimeException {
    public SearchableDocumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchableDocumentException(String message) {
        super(message);
    }
}
