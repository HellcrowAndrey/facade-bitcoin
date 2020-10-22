package com.github.wrapper.bitcoin.config;

import com.github.wrapper.bitcoin.repository.BlockSoChainRepository;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Objects;

public final class AppConfig {

    private static AppConfig instance;

    private static Retrofit retrofit;

    private AppConfig() {
    }

    public static AppConfig getInstance() {
        if (Objects.isNull(instance)) {
            instance = new AppConfig();
        }
        return instance;
    }

    private Retrofit getRetrofit(String url) {
        if (Objects.isNull(retrofit)) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public BlockSoChainRepository blockSoChainService(String url) {
        Retrofit retrofit = getRetrofit(url);
        return retrofit.create(BlockSoChainRepository.class);
    }

}
