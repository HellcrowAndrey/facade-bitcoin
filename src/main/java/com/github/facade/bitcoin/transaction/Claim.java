package com.github.facade.bitcoin.transaction;

public enum Claim {

    def, full;

    public final boolean isFull() {
        return this.equals(full);
    }

}
