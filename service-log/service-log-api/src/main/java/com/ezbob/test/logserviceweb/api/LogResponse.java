package com.ezbob.test.logserviceweb.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class LogResponse {
    private String msg;

    @JsonCreator
    public LogResponse(@JsonProperty("msg") String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogResponse that = (LogResponse) o;
        return Objects.equals(msg, that.msg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msg);
    }
}
