package com.github.wrapper.bitcoin.facade;

import com.github.wrapper.bitcoin.model.ChainAddress;
import com.github.wrapper.bitcoin.model.KeysBag;
import com.github.wrapper.bitcoin.model.NewBlock;
import com.github.wrapper.bitcoin.model.ResponseTrx;
import com.github.wrapper.bitcoin.payload.BlockChainInfo;
import com.github.wrapper.bitcoin.utils.Network;
import org.bitcoinj.core.NetworkParameters;

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
