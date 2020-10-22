package com.github.facade.bitcoin.function;

import com.github.facade.bitcoin.models.TOutput;
import com.github.facade.bitcoin.models.TransactionData;

public interface Incoming {

    void incoming(TransactionData data, TOutput output, String address, long height, String blockHash);

}
