package com.github.facade.bitcoin.exceptions;

public class BlockNumberException extends RuntimeException {

    public BlockNumberException() {
    }

    public BlockNumberException(String message) {
        super(message);
    }
}
