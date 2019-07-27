package com.parkpersonally.exception;

public class NoSuchParkingOrderException extends RuntimeException{

    public NoSuchParkingOrderException() {
    }

    public NoSuchParkingOrderException(String message) {
        super(message);
    }
}
