package com.ezbob.test.serviceshuffle.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ShuffleResponse {
    private List<Integer> list;

    @JsonCreator
    public ShuffleResponse(@JsonProperty("list") List<Integer> list) {
        this.list = list;
    }

    public List<Integer> getList() {
        return list;
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
