package com.ezbob.test.serviceshuffle.factory;

import com.ezbob.test.serviceshuffle.helper.ObjectMapperHelper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpRequestFactory {
    public static <T> HttpRequest makePostRequestFor(String url, T body) throws JsonProcessingException {
        String bodyAsString = ObjectMapperHelper.requestAsString(body);
        return HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(bodyAsString))
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .build();
    }
}
