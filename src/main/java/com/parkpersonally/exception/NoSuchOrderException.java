package com.parkpersonally.exception;

public class NoSuchOrderException extends RuntimeException {
    public NoSuchOrderException() {
    }

    public NoSuchOrderException(String message) {
        super(message);
    }
}
