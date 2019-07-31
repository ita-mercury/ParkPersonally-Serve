package com.parkpersonally.exception;

public class IllegalAuthException extends RuntimeException {
    public IllegalAuthException() {
    }

    public IllegalAuthException(String message) {
        super(message);
    }
}
