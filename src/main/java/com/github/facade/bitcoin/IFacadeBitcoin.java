package com.github.facade.bitcoin;

import com.github.facade.bitcoin.models.ChainAddress;
import com.github.facade.bitcoin.models.KeysBag;
import com.github.facade.bitcoin.models.NewBlock;
import com.github.facade.bitcoin.models.ResponseTrx;
import com.github.facade.bitcoin.payloads.BlockChainInfo;
import com.github.facade.bitcoin.utils.Network;

import java.util.List;
import java.util.function.Consumer;

public interface IFacadeBitcoin {

    KeysBag generateKeys();

    List<ChainAddress> generateAddresses(KeysBag keys, int start, int amount);

    ResponseTrx send(String transaction);

    void networking();

    NewBlock fetchBlock(String hash, long height);

    NewBlock fetchBlock(Long height, String url, Consumer<NewBlock> blocks);

    BlockChainInfo fetchInfo(String url);

    Network getNetwork();

    String getDerivation();

}
