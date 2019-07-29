package com.parkpersonally.exception;

public class NoSuchManagerException extends RuntimeException{

    public NoSuchManagerException() {
    }

    public NoSuchManagerException(String message) {
        super(message);
    }
}
