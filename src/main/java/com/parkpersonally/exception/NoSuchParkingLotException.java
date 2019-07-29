package com.parkpersonally.exception;

public class NoSuchParkingLotException extends RuntimeException {
    public NoSuchParkingLotException() {
    }

    public NoSuchParkingLotException(String message) {
        super(message);
    }
}
