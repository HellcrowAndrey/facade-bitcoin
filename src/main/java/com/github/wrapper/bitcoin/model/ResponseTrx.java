package com.github.wrapper.bitcoin.model;

import java.util.List;
import java.util.Objects;

public final class ResponseTrx {

    private final String hash;

    private final List<SpentInput> spentInputs;

    private final boolean hasError;

    private final Error error;

    public ResponseTrx(String hash, List<SpentInput> spentInputs, boolean hasError, Error error) {
        this.hash = hash;
        this.spentInputs = spentInputs;
        this.hasError = hasError;
        this.error = error;
    }

    public String getHash() {
        return hash;
    }

    public List<SpentInput> getSpentInputs() {
        return spentInputs;
    }

    public boolean isHasError() {
        return hasError;
    }

    public Error getError() {
        return error;
    }

    public final static class Error {

        private final String message;

        public Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Error error = (Error) o;
            return Objects.equals(message, error.message);
        }

        @Override
        public int hashCode() {
            return Objects.hash(message);
        }

        @Override
        public String toString() {
            return "Error{" +
                    "message='" + message + '\'' +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseTrx that = (ResponseTrx) o;
        return Objects.equals(hash, that.hash) &&
                Objects.equals(spentInputs, that.spentInputs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, spentInputs);
    }

    @Override
    public String toString() {
        return "ResponseTrx{" +
                "hash='" + hash + '\'' +
                ", spentInputs=" + spentInputs +
                '}';
    }
}
