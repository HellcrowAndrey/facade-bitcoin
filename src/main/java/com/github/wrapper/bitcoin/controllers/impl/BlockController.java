package com.github.wrapper.bitcoin.controllers.impl;

import com.github.wrapper.bitcoin.config.AppConfig;
import com.github.wrapper.bitcoin.controllers.IBlockController;
import com.github.wrapper.bitcoin.payload.BlockChainInfo;
import com.github.wrapper.bitcoin.payload.BlockHashSoChain;
import com.github.wrapper.bitcoin.payload.BlockHeightAdapter;
import com.github.wrapper.bitcoin.services.IBlockSoChainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Optional;

public final class BlockController implements IBlockController {

    private static final Logger log = LoggerFactory.getLogger(BlockController.class);

    private final String url;

    public BlockController(String url) {
        this.url = url;
    }

    @Override
    public BlockChainInfo findBlockChainInfo() {
        IBlockSoChainService service = AppConfig.getInstance().blockSoChainService(this.url);
        Call<BlockChainInfo> call = service.findBlockChainInfo();
        try {
            Response<BlockChainInfo> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
        } catch (IOException e) {
            log.warn("Enter: {}", e.getMessage());
        }
        return null;
    }

    @Override
    public Optional<BlockHeightAdapter> findBlockHash(Long height) {
        IBlockSoChainService service = AppConfig.getInstance().blockSoChainService(this.url);
        Call<BlockHashSoChain> call = service.findBlockHash(height);
        try {
            Response<BlockHashSoChain> response = call.execute();
            if (response.isSuccessful()) {
                return Optional.ofNullable(response.body());
            }
        } catch (IOException e) {
            log.warn("Enter: {}", e.getMessage());
        }
        return Optional.empty();
    }

}
