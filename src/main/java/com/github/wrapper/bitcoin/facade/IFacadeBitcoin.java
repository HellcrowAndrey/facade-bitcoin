package com.github.wrapper.bitcoin.facade;

import com.github.wrapper.bitcoin.model.ChainAddress;
import com.github.wrapper.bitcoin.model.KeysBag;
import com.github.wrapper.bitcoin.model.NewBlock;

import java.util.List;
import java.util.function.Consumer;

public interface IFacadeBitcoin {

    KeysBag generateKeys();

    List<ChainAddress> generateAddresses(KeysBag keys, int start, int amount);

    String send(String transaction);

    void networking();

    NewBlock fetchBlock(String hash, long height);

    NewBlock fetchBlock(Long height, String url, Consumer<NewBlock> blocks);

}
