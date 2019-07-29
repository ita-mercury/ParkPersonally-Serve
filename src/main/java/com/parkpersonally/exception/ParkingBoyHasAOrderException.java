package com.parkpersonally.exception;

public class ParkingBoyHasAOrderException extends RuntimeException {
    public ParkingBoyHasAOrderException() {
    }

    public ParkingBoyHasAOrderException(String message) {
        super(message);
    }
}
