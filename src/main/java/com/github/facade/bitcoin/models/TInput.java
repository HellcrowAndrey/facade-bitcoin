package com.github.facade.bitcoin.models;

import java.util.Objects;

public final class TInput {

    private final String address;

    private final long index;

    private final String hash;

    public TInput(String address, long index, String hash) {
        this.address = address;
        this.index = index;
        this.hash = hash;
    }

    public String getAddress() {
        return address;
    }

    public long getIndex() {
        return index;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TInput tInput = (TInput) o;
        return index == tInput.index &&
                Objects.equals(address, tInput.address) &&
                Objects.equals(hash, tInput.hash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, index, hash);
    }

    @Override
    public String toString() {
        return "TInput{" +
                "address='" + address + '\'' +
                ", index=" + index +
                ", hash='" + hash + '\'' +
                '}';
    }
}
