package com.github.wrapper.bitcoin.function;

import com.github.wrapper.bitcoin.model.TOutput;
import com.github.wrapper.bitcoin.model.TransactionData;

public interface Incoming {

    void incoming(TransactionData data, TOutput output, String address, long height, String blockHash);

}
