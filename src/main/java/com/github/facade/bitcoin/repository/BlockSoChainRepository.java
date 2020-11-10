package com.github.facade.bitcoin.repository;

import com.github.facade.bitcoin.payloads.BlockChainInfo;
import com.github.facade.bitcoin.payloads.BlockHashSoChain;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BlockSoChainRepository {

    @GET(value = "/api/v2/get_info/{network}")
    Call<BlockChainInfo> findBlockChainInfo(@Path(value = "network") String  network);

    @GET(value = "/api/v2/get_blockhash/{network}/{height}")
    Call<BlockHashSoChain> findBlockHash(@Path(value = "network") String  network,
                                         @Path(value = "height") Long height);

}
