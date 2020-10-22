package com.github.facade.bitcoin.payloads;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "data"
})
public class BlockHashSoChain implements BlockHeightAdapter {

    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    private Data data;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockHashSoChain that = (BlockHashSoChain) o;
        return Objects.equals(status, that.status) &&
                Objects.equals(data, that.data) &&
                Objects.equals(additionalProperties, that.additionalProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, data, additionalProperties);
    }

    @Override
    public String toString() {
        return "BlockResponse{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    @Override
    public String getBlockHash() {
        return this.data.getBlockHash();
    }

    @Override
    public Integer getBlockNumber() {
        return this.data.getBlockNo();
    }
}
