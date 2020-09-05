package com.ezbob.test.logserviceweb.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LogRequest {

    private String logInput;

    @JsonCreator
    public LogRequest(@JsonProperty("logInput") String logInput) {
        this.logInput = logInput;
    }

    public String getLogInput() {
        return logInput;
    }
}
