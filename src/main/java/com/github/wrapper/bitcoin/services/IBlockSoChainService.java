package com.github.wrapper.bitcoin.services;

import com.github.wrapper.bitcoin.payload.BlockHashSoChain;
import com.github.wrapper.bitcoin.payload.BlockHeightAdapter;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IBlockSoChainService {

    @GET(value = "/api/v2/get_blockhash/BTC/{height}")
    Call<BlockHashSoChain> findBlockHash(@Path(value = "height") Long height);

}
