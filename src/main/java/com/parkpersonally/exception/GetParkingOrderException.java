package com.parkpersonally.exception;

public class GetParkingOrderException extends RuntimeException {
    public GetParkingOrderException() {
    }

    public GetParkingOrderException(String message) {
        super(message);
    }
}
