package com.parkpersonally.exception;

public class UpdateParkingLotCapacitySmallerException extends RuntimeException {
    public UpdateParkingLotCapacitySmallerException() {
    }

    public UpdateParkingLotCapacitySmallerException(String message) {
        super(message);
    }
}
