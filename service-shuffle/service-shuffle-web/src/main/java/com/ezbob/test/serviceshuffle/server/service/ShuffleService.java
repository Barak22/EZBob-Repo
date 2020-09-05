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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShuffleService {

    private String serviceLogBaseUrl;

    public ShuffleService(@Value("${api.serviceLogBaseUrl}") String serviceLogBaseUrl) {
        this.serviceLogBaseUrl = serviceLogBaseUrl;
    }

    public List<Integer> generateRandomArray(ShuffleRequest shuffleRequest) {
        try {
            sendRequestForLog(shuffleRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doGeneration(shuffleRequest.getNumberForRandomArray());
    }

    private void sendRequestForLog(ShuffleRequest shuffleRequest) throws IOException {
        String requestAsString = new ObjectMapper().writeValueAsString(shuffleRequest);
        LogRequest logRequest = new LogRequest(requestAsString);

        HttpRequest req = HttpRequestFactory.makePostRequestFor(serviceLogBaseUrl + "/log", logRequest);

        // Async request
        HttpClient.newHttpClient().sendAsync(req, HttpResponse.BodyHandlers.ofString());
    }

    List<Integer> doGeneration(int numberForRandomArray) {
        List<Integer> list = Stream.iterate(1, i -> i != numberForRandomArray + 1, i -> i + 1).collect(Collectors.toList());

        // According the implementation, this is O(n)
        Collections.shuffle(list);
        return list;
    }
}
