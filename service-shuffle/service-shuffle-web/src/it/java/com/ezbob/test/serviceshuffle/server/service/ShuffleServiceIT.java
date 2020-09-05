package com.ezbob.test.serviceshuffle.server.service;

import com.ezbob.test.logserviceweb.testkit.LogServiceTestKit;
import com.ezbob.test.logserviceweb.testkit.server.service.FakeLoggerService;
import com.ezbob.test.serviceshuffle.api.ShuffleRequest;
import com.ezbob.test.serviceshuffle.api.ShuffleResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;

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

        EventuallyConsistent.eventually(Duration.ofSeconds(1),
                () -> assertThat("shuffleRequest has not been written to the logs",
                        FakeLoggerService.getLogsList(),
                        contains(is(expected))));
    }

    @Test
    public void shouldReturnInvalidInputResponse() {
        ShuffleResponse actual = service.generateRandomArray(new ShuffleRequest(-1));
        ShuffleResponse expected = ShuffleResponse.invalidResponse();

        assertThat("Lists are not equal", actual, IsEqual.equalTo(expected));

        actual = service.generateRandomArray(new ShuffleRequest(1001));
        expected = ShuffleResponse.invalidResponse();

        assertThat("Lists are not equal", actual, IsEqual.equalTo(expected));
    }

    private ShuffleRequest createShuffleRequestWith(int numberForRandomArray) {
        return new ShuffleRequest(numberForRandomArray);
    }
}


// Found that on the internet
class EventuallyConsistent {

    public static void eventually(Duration maxDuration, Runnable command) {
        final Instant start = Instant.now();
        final Instant max = start.plus(maxDuration);

        boolean failed;
        do {
            try {
                command.run();
                failed = false;
            } catch (Throwable t) {
                failed = true;
                if (Instant.now().isBefore(max)) {
                    // Try again after a short nap
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    // Max duration has exceeded, it took too long to become consistent
                    throw t;
                }
            }
        } while (failed);
    }
}