package com.ezbob.test.serviceshuffle.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShuffleRequest {

    private int numberForRandomArray;

    @JsonCreator
    public ShuffleRequest(@JsonProperty("numberForRandomArray") int numberForRandomArray) {
        this.numberForRandomArray = numberForRandomArray;
    }

    public int getNumberForRandomArray() {
        return numberForRandomArray;
    }
}
