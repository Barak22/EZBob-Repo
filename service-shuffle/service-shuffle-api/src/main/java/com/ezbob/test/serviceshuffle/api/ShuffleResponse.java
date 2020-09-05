package com.ezbob.test.serviceshuffle.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ShuffleResponse {
    private List<Integer> list;
    private boolean isSuccessful;
    private String errorMessage;

    public static ShuffleResponse invalidResponse() {
        return new ShuffleResponse(Collections.emptyList(), false, "Number must be between 1 to 1000");
    }

    public static ShuffleResponse validResponseWith(List<Integer> list) {
        return new ShuffleResponse(list, true, "");
    }

    @JsonCreator
    public ShuffleResponse(@JsonProperty("list") List<Integer> list,
                           @JsonProperty("isSuccessful") boolean isSuccessful,
                           @JsonProperty("errorMessage") String errorMessage) {
        this.list = list;
        this.isSuccessful = isSuccessful;
        this.errorMessage = errorMessage;
    }

    public List<Integer> getList() {
        return list;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShuffleResponse that = (ShuffleResponse) o;
        Collections.sort(that.getList());
        return this.getList().equals(that.getList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }
}
