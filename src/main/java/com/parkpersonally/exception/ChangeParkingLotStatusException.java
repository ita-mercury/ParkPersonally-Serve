package com.parkpersonally.exception;

public class ChangeParkingLotStatusException extends RuntimeException {
    public ChangeParkingLotStatusException() {
    }

    public ChangeParkingLotStatusException(String message) {
        super(message);
    }
}
