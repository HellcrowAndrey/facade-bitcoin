package com.github.facade.bitcoin.services;

import com.github.facade.bitcoin.payloads.BlockChainInfo;
import com.github.facade.bitcoin.payloads.BlockHeightAdapter;

import java.util.Optional;

public interface IBlockSoChainService {

    BlockChainInfo findBlockChainInfo();

    Optional<BlockHeightAdapter> findBlockHash(Long height);

}
