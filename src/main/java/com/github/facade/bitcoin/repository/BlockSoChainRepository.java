package com.github.facade.bitcoin.repository;

import com.github.facade.bitcoin.payloads.BlockChainInfo;
import com.github.facade.bitcoin.payloads.BlockHashSoChain;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BlockSoChainRepository {

    @GET(value = "/api/v2/get_info/BTC")
    Call<BlockChainInfo> findBlockChainInfo();

    @GET(value = "/api/v2/get_blockhash/BTC/{height}")
    Call<BlockHashSoChain> findBlockHash(@Path(value = "height") Long height);

}
