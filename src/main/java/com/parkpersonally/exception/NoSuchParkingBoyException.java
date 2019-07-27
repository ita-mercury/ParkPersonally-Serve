package com.parkpersonally.exception;

import java.util.function.Supplier;

public class NoSuchParkingBoyException extends RuntimeException {

    public NoSuchParkingBoyException() {
        
    }

    public NoSuchParkingBoyException(String message) {
        super(message);
    }
}
