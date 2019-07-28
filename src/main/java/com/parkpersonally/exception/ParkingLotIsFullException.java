package com.parkpersonally.exception;

public class ParkingLotIsFullException extends RuntimeException {
    public ParkingLotIsFullException() {
    }

    public ParkingLotIsFullException(String message) {
        super(message);
    }
}
