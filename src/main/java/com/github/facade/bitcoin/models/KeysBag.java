package com.github.facade.bitcoin.models;

import java.util.Objects;

public final class KeysBag {

    private final String mnemonic;

    private final String privateKey;

    private final String publicKey;

    private final String chainCode;

    private final long timeStamp;

    public KeysBag(String mnemonic, String privateKey, String publicKey, String chainCode, long timeStamp) {
        this.mnemonic = mnemonic;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.chainCode = chainCode;
        this.timeStamp = timeStamp;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getChainCode() {
        return chainCode;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeysBag keysBag = (KeysBag) o;
        return timeStamp == keysBag.timeStamp &&
                Objects.equals(mnemonic, keysBag.mnemonic) &&
                Objects.equals(privateKey, keysBag.privateKey) &&
                Objects.equals(publicKey, keysBag.publicKey) &&
                Objects.equals(chainCode, keysBag.chainCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mnemonic, privateKey, publicKey, chainCode, timeStamp);
    }

    @Override
    public String toString() {
        return "KeysBag{" +
                "mnemonic='" + mnemonic + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", chainCode='" + chainCode + '\'' +
                ", timeStamp=" + timeStamp +
                '}';
    }

}
