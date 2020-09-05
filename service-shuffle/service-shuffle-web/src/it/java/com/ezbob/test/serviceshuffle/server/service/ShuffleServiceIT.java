package com.ezbob.test.serviceshuffle.server.service;

import com.ezbob.test.logserviceweb.testkit.LogServiceTestKit;
import com.ezbob.test.logserviceweb.testkit.server.service.FakeLoggerService;
import com.ezbob.test.serviceshuffle.api.ShuffleRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.ezbob.test.serviceshuffle.helper.ObjectMapperHelper.requestAsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;


public class ShuffleServiceIT {
    private static int serviceLogPort = 6000;
    private ShuffleService service = new ShuffleService("http://localhost:" + serviceLogPort);

    @BeforeAll
    public static void before() {
        LogServiceTestKit.setPort(serviceLogPort);
        LogServiceTestKit.start();
    }

    @Test
    public void sendRequestsToLogService() throws JsonProcessingException {
        int irrelevantNumber = 5;
        ShuffleRequest shuffleRequest = createShuffleRequestWith(irrelevantNumber);
        String expected = requestAsString(shuffleRequest);

        service.generateRandomArray(shuffleRequest);

        assertThat("shuffleRequest has not been written to the logs",
                FakeLoggerService.getLogsList(),
                contains(is(expected)));
    }

    private ShuffleRequest createShuffleRequestWith(int numberForRandomArray) {
        return new ShuffleRequest(numberForRandomArray);
    }
}
