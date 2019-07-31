package com.parkpersonally.exception;

public class ManagerFreezeFailException extends RuntimeException {
    public ManagerFreezeFailException() {
    }

    public ManagerFreezeFailException(String message) {
        super(message);
    }
}
