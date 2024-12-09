package com.rehneo.bookcreaturebackend.admin;

public class RequestAlreadyProcessedException extends RuntimeException{
    public RequestAlreadyProcessedException(String message){
        super(message);
    }
}
