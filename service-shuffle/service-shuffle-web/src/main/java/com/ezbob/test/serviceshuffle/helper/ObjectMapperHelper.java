package com.ezbob.test.serviceshuffle.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperHelper {

    public static <T> String requestAsString(T request) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(request);
    }
}
