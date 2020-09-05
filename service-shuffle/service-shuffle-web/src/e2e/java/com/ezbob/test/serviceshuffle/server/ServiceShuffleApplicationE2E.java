package com.ezbob.test.serviceshuffle.server;

import com.ezbob.test.logserviceweb.testkit.LogServiceTestKit;
import com.ezbob.test.serviceshuffle.api.ShuffleRequest;
import com.ezbob.test.serviceshuffle.api.ShuffleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import static com.ezbob.test.serviceshuffle.factory.HttpRequestFactory.makePostRequestFor;
import static com.ezbob.test.serviceshuffle.server.TheSameResponseMatcher.isTheSameResponseAs;
import static org.junit.Assert.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceShuffleApplicationE2E {

    @LocalServerPort
    int port;

    @BeforeAll
    public static void before() {
        LogServiceTestKit.setPort(6000);
        LogServiceTestKit.start();
    }

    @Test
    void getRandomArray() throws IOException, InterruptedException {
        ShuffleRequest shuffleRequest = new ShuffleRequest(5);

        ShuffleResponse actualShuffleResponse = executeRequestWith(shuffleRequest);

        ShuffleResponse expectedShuffleResponse = new ShuffleResponse(Arrays.asList(1, 2, 3, 4, 5));

        assertThat("The responses are not equal",
                actualShuffleResponse,
                isTheSameResponseAs(expectedShuffleResponse));
    }

    private ShuffleResponse executeRequestWith(ShuffleRequest shuffleRequest) throws IOException, InterruptedException {
        HttpRequest req = makePostRequestFor("http://localhost:" + port + "/shuffle", shuffleRequest);

        HttpResponse<String> res = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());
        return new ObjectMapper()
                .readerFor(ShuffleResponse.class)
                .readValue(res.body());
    }
}

class TheSameResponseMatcher extends TypeSafeMatcher<ShuffleResponse> {

    ShuffleResponse expected;

    public TheSameResponseMatcher(ShuffleResponse expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(ShuffleResponse actual) {
        return expected.equals(actual);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expected.toString());
    }

    public static Matcher<ShuffleResponse> isTheSameResponseAs(ShuffleResponse expected) {
        return new TheSameResponseMatcher(expected);
    }
}