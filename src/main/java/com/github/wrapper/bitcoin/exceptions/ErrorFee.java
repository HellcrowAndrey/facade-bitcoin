package com.github.wrapper.bitcoin.exceptions;

public class ErrorFee extends RuntimeException {

    public ErrorFee() {
    }

    public ErrorFee(String message) {
        super(message);
    }
}
