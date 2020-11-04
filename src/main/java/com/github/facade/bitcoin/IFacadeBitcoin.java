package com.github.facade.bitcoin;

import com.github.facade.bitcoin.models.ChainAddress;
import com.github.facade.bitcoin.models.KeysBag;
import com.github.facade.bitcoin.models.NewBlock;
import com.github.facade.bitcoin.models.ResponseTrx;
import com.github.facade.bitcoin.payloads.BlockChainInfo;
import com.github.facade.bitcoin.payloads.BlockHeightAdapter;
import com.github.facade.bitcoin.utils.Network;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IFacadeBitcoin {

    KeysBag generateKeys();

    List<ChainAddress> generateAddresses(KeysBag keys, int start, int amount);

    ResponseTrx send(String transaction);

    void networking();

    NewBlock fetchBlock(String hash, long height);

    void addBlockListener(Long height, String url, Consumer<NewBlock> blocks);

    void addBlockListener(Long height, Function<Long, Optional<BlockHeightAdapter>> service, Consumer<NewBlock> blocks);

    BlockChainInfo fetchInfo(String url);

    Network getNetwork();

    String getDerivation();

}
