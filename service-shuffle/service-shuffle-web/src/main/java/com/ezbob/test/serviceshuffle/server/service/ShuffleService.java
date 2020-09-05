package com.ezbob.test.serviceshuffle.server.service;

import com.ezbob.test.serviceshuffle.api.ShuffleRequest;
import com.ezbob.test.serviceshuffle.api.ShuffleResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ShuffleService {


    private LoggerClient loggerClient;

    public ShuffleService(LoggerClient loggerClient) {
        this.loggerClient = loggerClient;
    }

    public ShuffleResponse generateRandomArray(ShuffleRequest shuffleRequest) {
        try {
            loggerClient.sendRequestForLog(shuffleRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!isInputValid(shuffleRequest))
            return ShuffleResponse.invalidResponse();

        List<Integer> list = doGeneration(shuffleRequest.getNumberForRandomArray());
        return ShuffleResponse.validResponseWith(list);
    }

    private boolean isInputValid(ShuffleRequest shuffleRequest) {
        return shuffleRequest.getNumberForRandomArray() >= 1 &&
                shuffleRequest.getNumberForRandomArray() <= 1000;
    }

    List<Integer> doGeneration(int numberForRandomArray) {
        List<Integer> list = Stream.iterate(1, i -> i != numberForRandomArray + 1, i -> i + 1).collect(Collectors.toList());

        // According the implementation, this is O(n)
        Collections.shuffle(list);
        return list;
    }
}
