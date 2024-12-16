package com.rehneo.bookcreaturebackend.fileimport.error;

public class BadFileExtensionException extends RuntimeException {
    public BadFileExtensionException(String message) {
        super(message);
    }
}
