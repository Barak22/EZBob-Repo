package com.ezbob.test.serviceshuffle.server.service;

import com.ezbob.test.logserviceweb.api.LogRequest;
import com.ezbob.test.serviceshuffle.api.ShuffleRequest;
import com.ezbob.test.serviceshuffle.factory.HttpRequestFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class LoggerClient {
    private String serviceLogBaseUrl;

    public LoggerClient(@Value("${api.serviceLogBaseUrl}") String serviceLogBaseUrl) {
        this.serviceLogBaseUrl = serviceLogBaseUrl;
    }

    public void sendRequestForLog(ShuffleRequest shuffleRequest) throws IOException {
        String requestAsString = new ObjectMapper().writeValueAsString(shuffleRequest);
        LogRequest logRequest = new LogRequest(requestAsString);

        HttpRequest req = HttpRequestFactory.makePostRequestFor(serviceLogBaseUrl + "/log", logRequest);

        // Async request
        HttpClient.newHttpClient().sendAsync(req, HttpResponse.BodyHandlers.ofString());
    }
}
