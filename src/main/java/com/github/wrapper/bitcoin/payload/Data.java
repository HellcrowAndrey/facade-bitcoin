package com.github.wrapper.bitcoin.payload;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "network",
        "blockhash",
        "block_no"
})
public class Data {

    @JsonProperty("network")
    private String network;

    @JsonProperty("blockhash")
    private String blockHash;

    @JsonProperty("block_no")
    private Integer blockNo;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("network")
    public String getNetwork() {
        return network;
    }

    @JsonProperty("blockhash")
    public String getBlockHash() {
        return blockHash;
    }

    @JsonProperty("block_no")
    public Integer getBlockNo() {
        return blockNo;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return Objects.equals(network, data.network) &&
                Objects.equals(blockHash, data.blockHash) &&
                Objects.equals(blockNo, data.blockNo) &&
                Objects.equals(additionalProperties, data.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(network, blockHash, blockNo, additionalProperties);
    }

    @Override
    public String toString() {
        return "Data{" +
                "network='" + network + '\'' +
                ", blockHash='" + blockHash + '\'' +
                ", blockNo=" + blockNo +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
