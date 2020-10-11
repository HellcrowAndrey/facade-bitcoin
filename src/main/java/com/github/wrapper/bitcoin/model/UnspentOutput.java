package com.github.wrapper.bitcoin.model;

import java.math.BigDecimal;
import java.util.Objects;

public final class UnspentOutput {

    private final String address;

    private final boolean isChange;

    private final BigDecimal amount;

    private final Integer index;

    private final String txOutScriptPubKey;

    private final String txHash;

    private final String pubKeyHash;

    public UnspentOutput(String address,  boolean isChange, BigDecimal amount, Integer index, String txOutScriptPubKey, String txHash, String pubKeyHash) {
        this.address = address;
        this.isChange = isChange;
        this.amount = amount;
        this.index = index;
        this.txOutScriptPubKey = txOutScriptPubKey;
        this.txHash = txHash;
        this.pubKeyHash = pubKeyHash;
    }

    public String getAddress() {
        return address;
    }

    public boolean isChange() {
        return isChange;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Integer getIndex() {
        return index;
    }

    public String getTxOutScriptPubKey() {
        return txOutScriptPubKey;
    }

    public String getTxHash() {
        return txHash;
    }

    public String getPubKeyHash() {
        return pubKeyHash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnspentOutput that = (UnspentOutput) o;
        return isChange == that.isChange &&
                Objects.equals(address, that.address) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(index, that.index) &&
                Objects.equals(txOutScriptPubKey, that.txOutScriptPubKey) &&
                Objects.equals(txHash, that.txHash) &&
                Objects.equals(pubKeyHash, that.pubKeyHash);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, isChange, amount, index, txOutScriptPubKey, txHash, pubKeyHash);
    }

    @Override
    public String toString() {
        return "UnspentOutput{" +
                "address='" + address + '\'' +
                ", isChange=" + isChange +
                ", amount=" + amount +
                ", index=" + index +
                ", txOutScriptPubKey='" + txOutScriptPubKey + '\'' +
                ", txHash='" + txHash + '\'' +
                ", pubKeyHash='" + pubKeyHash + '\'' +
                '}';
    }
}
