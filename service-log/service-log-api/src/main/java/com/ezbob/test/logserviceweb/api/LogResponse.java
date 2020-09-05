package com.ezbob.test.logserviceweb.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class LogResponse {
    private boolean isSuccessful;

    @JsonCreator
    public LogResponse(@JsonProperty("isSuccessful") boolean isSuccessful) {
        this.isSuccessful = isSuccessful;
    }

    public boolean getSuccessful() {
        return isSuccessful;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogResponse that = (LogResponse) o;
        return Objects.equals(isSuccessful, that.isSuccessful);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSuccessful);
    }
}
