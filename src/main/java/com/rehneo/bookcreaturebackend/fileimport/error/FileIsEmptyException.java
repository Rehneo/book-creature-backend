package com.rehneo.bookcreaturebackend.fileimport.error;

public class FileIsEmptyException extends RuntimeException {
    public FileIsEmptyException(String message) {
        super(message);
    }
}
