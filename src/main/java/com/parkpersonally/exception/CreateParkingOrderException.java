package com.parkpersonally.exception;

public class CreateParkingOrderException extends RuntimeException {
    public CreateParkingOrderException() {
    }

    public CreateParkingOrderException(String message) {
        super(message);
    }
}
