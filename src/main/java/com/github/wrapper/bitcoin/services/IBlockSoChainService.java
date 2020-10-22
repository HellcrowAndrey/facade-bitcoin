package com.github.wrapper.bitcoin.services;

import com.github.wrapper.bitcoin.payload.BlockChainInfo;
import com.github.wrapper.bitcoin.payload.BlockHeightAdapter;

import java.util.Optional;

public interface IBlockSoChainService {

    BlockChainInfo findBlockChainInfo();

    Optional<BlockHeightAdapter> findBlockHash(Long height);

}
