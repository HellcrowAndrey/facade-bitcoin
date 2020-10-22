package com.github.facade.bitcoin.models;

import java.util.Objects;

public final class ChainAddress {

    private final int index;

    private final String address;

    public ChainAddress(int index, String address) {
        this.index = index;
        this.address = address;
    }

    public int getIndex() {
        return index;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChainAddress that = (ChainAddress) o;
        return index == that.index &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, address);
    }

    @Override
    public String toString() {
        return "ChainAddress{" +
                "index=" + index +
                ", address='" + address + '\'' +
                '}';
    }
}
