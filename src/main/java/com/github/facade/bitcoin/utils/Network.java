package com.github.facade.bitcoin.utils;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;

public enum Network {

    MAIN,
    PROD,
    TEST,
    REGTEST;

    public final NetworkParameters get() {
        switch(this) {
            case MAIN:
            case PROD:
                return MainNetParams.get();
            case TEST:
                return TestNet3Params.get();
            case REGTEST:
            default:
                return RegTestParams.get();
        }
    }

    public final String getApi() {
        switch(this) {
            case MAIN:
                return "BTC";
            case TEST:
                return  "BTCTEST";
            default:
                return "BTC";
        }
    }

}
