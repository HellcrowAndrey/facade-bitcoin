package com.github.wrapper.bitcoin.function;

import com.github.wrapper.bitcoin.model.TransactionData;

public interface Outgoing {

    void outgoing(TransactionData data, long height, String blockHash);

}
