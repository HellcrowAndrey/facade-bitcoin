package com.github.wrapper.bitcoin.model;

import java.util.List;
import java.util.Objects;

public final class NewBlock {

    private final long height;

    private final String hash;

    private final List<TransactionData> transactions;

    public NewBlock(long height, String hash, List<TransactionData> transactions) {
        this.height = height;
        this.hash = hash;
        this.transactions = transactions;
    }

    public long getHeight() {
        return height;
    }

    public String getHash() {
        return hash;
    }

    public List<TransactionData> getTransactions() {
        return transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewBlock newBlock = (NewBlock) o;
        return height == newBlock.height &&
                Objects.equals(hash, newBlock.hash) &&
                Objects.equals(transactions, newBlock.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, hash, transactions);
    }

    @Override
    public String toString() {
        return "NewBlock{" +
                "height=" + height +
                ", hash='" + hash + '\'' +
                ", size='" + transactions.size() + '\'' +
                '}';
    }

}
