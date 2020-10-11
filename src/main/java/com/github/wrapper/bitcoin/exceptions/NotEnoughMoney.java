package com.github.wrapper.bitcoin.exceptions;

public class NotEnoughMoney extends RuntimeException {

    public NotEnoughMoney() {
    }

    public NotEnoughMoney(String message) {
        super(message);
    }
}
