package com.rehneo.bookcreaturebackend.error;

import lombok.Getter;

import java.util.List;

@Getter
public class Response {
    private final String message;

    public Response(String message) {
        this.message = message;
    }

    public Response(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

    private List<String> details;
}
