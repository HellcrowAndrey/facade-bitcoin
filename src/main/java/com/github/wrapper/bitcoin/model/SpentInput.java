package com.github.wrapper.bitcoin.model;

import org.bitcoinj.core.TransactionInput;

import java.util.Objects;

public final class SpentInput {

    private final long index;

    private final String hash;

    public SpentInput(long index, String hash) {
        this.index = index;
        this.hash = hash;
    }

    public long getIndex() {
        return index;
    }

    public String getHash() {
        return hash;
    }

    public static SpentInput instance(TransactionInput input) {
         return new SpentInput(input.getIndex(), input.getOutpoint().getHash().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpentInput that = (SpentInput) o;
        return index == that.index &&
                Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, hash);
    }

    @Override
    public String toString() {
        return "SpentInput{" +
                "index=" + index +
                ", hash='" + hash + '\'' +
                '}';
    }
}
