package com.github.facade.bitcoin.models;

import com.github.facade.bitcoin.function.Incoming;
import com.github.facade.bitcoin.function.Outgoing;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

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

    public void transactions(Incoming incoming, Outgoing outgoing, Supplier<List<String>> addressFunc) {
        List<String> addresses = addressFunc.get();
        addresses.forEach(address -> this.transactions
                .forEach(transaction -> searchTransactions(incoming, outgoing, transaction, address, height, hash)));
    }

    private void searchTransactions(Incoming incoming, Outgoing outgoing, TransactionData transaction, String address, long height, String blockHash) {
        incoming(incoming, transaction, address, height, blockHash);
        outgoing(outgoing, transaction, address, height, blockHash);
    }

    private void incoming(Incoming incoming, TransactionData data, String address, long height, String blockHash) {
        data.getOutputs().stream()
                .filter(output -> address.equals(output.getAddress()))
                .findAny()
                .ifPresent(output -> incoming.incoming(data, output, address, height, blockHash));
    }

    private void outgoing(Outgoing outgoing, TransactionData data, String address, long height, String blockHash) {
        data.getInputs().stream()
                .filter(input -> address.equals(input.getAddress()))
                .findAny()
                .ifPresent(input -> outgoing.outgoing(data, height, blockHash));
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
