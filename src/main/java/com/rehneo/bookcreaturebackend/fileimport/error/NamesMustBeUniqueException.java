package com.rehneo.bookcreaturebackend.fileimport.error;

public class NamesMustBeUniqueException extends RuntimeException {
    public NamesMustBeUniqueException(String message) {
        super(message);
    }
}
