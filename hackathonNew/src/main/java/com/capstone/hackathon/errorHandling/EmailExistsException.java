package com.capstone.hackathon.errorHandling;

public class EmailExistsException extends RuntimeException {//register time same email exe
    public EmailExistsException(String message) {
        super(message);
    }
}
