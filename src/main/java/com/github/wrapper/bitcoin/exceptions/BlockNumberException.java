package com.github.wrapper.bitcoin.exceptions;

public class BlockNumberException extends RuntimeException {

    public BlockNumberException() {
    }

    public BlockNumberException(String message) {
        super(message);
    }
}
