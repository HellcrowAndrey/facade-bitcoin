package com.github.wrapper.bitcoin.controllers;

import com.github.wrapper.bitcoin.payload.BlockHeightAdapter;

import java.util.Optional;

public interface IBlockController {

    Optional<BlockHeightAdapter> findBlockHash(Long height);

}
