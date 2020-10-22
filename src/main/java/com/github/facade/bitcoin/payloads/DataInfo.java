package com.github.facade.bitcoin.payloads;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "acronym",
        "network",
        "symbol_htmlcode",
        "url",
        "mining_difficulty",
        "unconfirmed_txs",
        "blocks",
        "price",
        "price_base",
        "price_update_time",
        "hashrate"
})
public class DataInfo {

    @JsonProperty("name")
    private String name;

    @JsonProperty("acronym")
    private String acronym;

    @JsonProperty("network")
    private String network;

    @JsonProperty("symbol_htmlcode")
    private String symbolHtmlcode;

    @JsonProperty("url")
    private String url;

    @JsonProperty("mining_difficulty")
    private String miningDifficulty;

    @JsonProperty("unconfirmed_txs")
    private Integer unconfirmedTxs;

    @JsonProperty("blocks")
    private Integer blocks;

    @JsonProperty("price")
    private String price;

    @JsonProperty("price_base")
    private String priceBase;

    @JsonProperty("price_update_time")
    private Integer priceUpdateTime;

    @JsonProperty("hashrate")
    private String hashrate;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("acronym")
    public String getAcronym() {
        return acronym;
    }

    @JsonProperty("acronym")
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    @JsonProperty("network")
    public String getNetwork() {
        return network;
    }

    @JsonProperty("network")
    public void setNetwork(String network) {
        this.network = network;
    }

    @JsonProperty("symbol_htmlcode")
    public String getSymbolHtmlcode() {
        return symbolHtmlcode;
    }

    @JsonProperty("symbol_htmlcode")
    public void setSymbolHtmlcode(String symbolHtmlcode) {
        this.symbolHtmlcode = symbolHtmlcode;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("mining_difficulty")
    public String getMiningDifficulty() {
        return miningDifficulty;
    }

    @JsonProperty("mining_difficulty")
    public void setMiningDifficulty(String miningDifficulty) {
        this.miningDifficulty = miningDifficulty;
    }

    @JsonProperty("unconfirmed_txs")
    public Integer getUnconfirmedTxs() {
        return unconfirmedTxs;
    }

    @JsonProperty("unconfirmed_txs")
    public void setUnconfirmedTxs(Integer unconfirmedTxs) {
        this.unconfirmedTxs = unconfirmedTxs;
    }

    @JsonProperty("blocks")
    public Integer getBlocks() {
        return blocks;
    }

    @JsonProperty("blocks")
    public void setBlocks(Integer blocks) {
        this.blocks = blocks;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("price_base")
    public String getPriceBase() {
        return priceBase;
    }

    @JsonProperty("price_base")
    public void setPriceBase(String priceBase) {
        this.priceBase = priceBase;
    }

    @JsonProperty("price_update_time")
    public Integer getPriceUpdateTime() {
        return priceUpdateTime;
    }

    @JsonProperty("price_update_time")
    public void setPriceUpdateTime(Integer priceUpdateTime) {
        this.priceUpdateTime = priceUpdateTime;
    }

    @JsonProperty("hashrate")
    public String getHashrate() {
        return hashrate;
    }

    @JsonProperty("hashrate")
    public void setHashrate(String hashrate) {
        this.hashrate = hashrate;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataInfo dataInfo = (DataInfo) o;
        return Objects.equals(name, dataInfo.name) &&
                Objects.equals(acronym, dataInfo.acronym) &&
                Objects.equals(network, dataInfo.network) &&
                Objects.equals(symbolHtmlcode, dataInfo.symbolHtmlcode) &&
                Objects.equals(url, dataInfo.url) &&
                Objects.equals(miningDifficulty, dataInfo.miningDifficulty) &&
                Objects.equals(unconfirmedTxs, dataInfo.unconfirmedTxs) &&
                Objects.equals(blocks, dataInfo.blocks) &&
                Objects.equals(price, dataInfo.price) &&
                Objects.equals(priceBase, dataInfo.priceBase) &&
                Objects.equals(priceUpdateTime, dataInfo.priceUpdateTime) &&
                Objects.equals(hashrate, dataInfo.hashrate) &&
                Objects.equals(additionalProperties, dataInfo.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, acronym, network, symbolHtmlcode,
                url, miningDifficulty, unconfirmedTxs, blocks,
                price, priceBase, priceUpdateTime, hashrate, additionalProperties);
    }

    @Override
    public String toString() {
        return "DataInfo{" +
                "name='" + name + '\'' +
                ", acronym='" + acronym + '\'' +
                ", network='" + network + '\'' +
                ", symbolHtmlcode='" + symbolHtmlcode + '\'' +
                ", url='" + url + '\'' +
                ", miningDifficulty='" + miningDifficulty + '\'' +
                ", unconfirmedTxs=" + unconfirmedTxs +
                ", blocks=" + blocks +
                ", price='" + price + '\'' +
                ", priceBase='" + priceBase + '\'' +
                ", priceUpdateTime=" + priceUpdateTime +
                ", hashrate='" + hashrate + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
