package com.rehneo.bookcreaturebackend.fileimport.error;

import lombok.Getter;

import java.util.List;

@Getter
public class JsonValidationException extends RuntimeException {
    private final List<String> errors;

    public JsonValidationException(String message, List<String> errors) {
        super(message);
        this.errors = errors;
    }
}
