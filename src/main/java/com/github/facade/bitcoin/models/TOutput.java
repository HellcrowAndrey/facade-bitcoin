package com.github.facade.bitcoin.models;

import java.util.Objects;

public final class TOutput {

    private final String address;

    private final int index;

    private final String pubKeyHash;

    private final String script;

    private final long value;

    public TOutput(String address, int index, String pubKeyHash, String script, long value) {
        this.address = address;
        this.index = index;
        this.pubKeyHash = pubKeyHash;
        this.script = script;
        this.value = value;
    }

    public String getAddress() {
        return address;
    }

    public int getIndex() {
        return index;
    }

    public String getPubKeyHash() {
        return pubKeyHash;
    }

    public String getScript() {
        return script;
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TOutput tOutput = (TOutput) o;
        return index == tOutput.index &&
                value == tOutput.value &&
                Objects.equals(address, tOutput.address) &&
                Objects.equals(pubKeyHash, tOutput.pubKeyHash) &&
                Objects.equals(script, tOutput.script);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, index, pubKeyHash, script, value);
    }

    @Override
    public String toString() {
        return "TOutput{" +
                "address='" + address + '\'' +
                ", index=" + index +
                ", pubKeyHash='" + pubKeyHash + '\'' +
                ", script='" + script + '\'' +
                ", value=" + value +
                '}';
    }
}
