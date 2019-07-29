package com.parkpersonally.exception;

public class ParkingBoyIsIllegalException extends RuntimeException{
    public ParkingBoyIsIllegalException() {
    }

    public ParkingBoyIsIllegalException(String message) {
        super(message);
    }
}
