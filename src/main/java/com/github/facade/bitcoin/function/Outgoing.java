package com.github.facade.bitcoin.function;

import com.github.facade.bitcoin.models.TransactionData;

public interface Outgoing {

    void outgoing(TransactionData data, long height, String blockHash);

}
