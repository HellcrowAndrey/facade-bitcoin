package com.github.wrapper.bitcoin.model;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.github.wrapper.bitcoin.utils.WrapUtils.fetchInput;
import static com.github.wrapper.bitcoin.utils.WrapUtils.fetchOutput;

public final class TransactionData {

    private static final int START_CONFIRM = 1;

    private final String hash;

    private final int confirmation;

    private final List<TInput> inputs;

    private final List<TOutput> outputs;

    public TransactionData(String hash, int confirmation, List<TInput> inputs, List<TOutput> outputs) {
        this.hash = hash;
        this.confirmation = confirmation;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public TransactionData(String hash, List<TInput> inputs, List<TOutput> outputs) {
        this.hash = hash;
        this.confirmation = START_CONFIRM;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public String getHash() {
        return hash;
    }

    public int getConfirmation() {
        return confirmation;
    }

    public List<TInput> getInputs() {
        return inputs;
    }

    public List<TOutput> getOutputs() {
        return outputs;
    }

    public static TransactionData transaction(Transaction t, NetworkParameters params) {
        List<TInput> inputs = t.getInputs().stream()
                .map(i -> fetchInput(i, params))
                .collect(Collectors.toList());
        List<TOutput> outputs = t.getOutputs().stream()
                .map(o -> fetchOutput(o, params))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new TransactionData(t.getTxId().toString(), inputs, outputs);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionData that = (TransactionData) o;
        return confirmation == that.confirmation &&
                Objects.equals(hash, that.hash) &&
                Objects.equals(inputs, that.inputs) &&
                Objects.equals(outputs, that.outputs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, confirmation, inputs, outputs);
    }

    @Override
    public String toString() {
        return "TransactionData{" +
                "hash='" + hash + '\'' +
                ", confirmation=" + confirmation +
                ", inputs=" + inputs +
                ", outputs=" + outputs +
                '}';
    }
}
