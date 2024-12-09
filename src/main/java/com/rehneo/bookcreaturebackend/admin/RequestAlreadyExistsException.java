package com.rehneo.bookcreaturebackend.admin;

public class RequestAlreadyExistsException extends RuntimeException{

    RequestAlreadyExistsException(String message){
        super(message);
    }
}
