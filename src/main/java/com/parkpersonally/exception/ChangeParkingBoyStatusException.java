package com.parkpersonally.exception;

public class ChangeParkingBoyStatusException extends RuntimeException{

    public ChangeParkingBoyStatusException() {
    }

    public ChangeParkingBoyStatusException(String message) {
        super(message);
    }
}
