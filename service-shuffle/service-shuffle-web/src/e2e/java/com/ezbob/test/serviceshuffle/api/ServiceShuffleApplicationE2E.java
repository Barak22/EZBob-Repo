package com.ezbob.test.serviceshuffle.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import static org.junit.Assert.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceShuffleApplicationE2E {

    @LocalServerPort
    int port;

    @Test
    void healthCheck() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:" + port + "/hello"))
                .build();

        HttpResponse<String> res = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());

        assertThat("The responses are not equal", res.body(), IsEqual.equalTo("Hello World!"));
    }

    @Test
    void getRandomArray() throws IOException, InterruptedException {
        ShuffleRequest shuffleRequest = new ShuffleRequest();
        shuffleRequest.setNumberForRandomArray(5);
        String body = new ObjectMapper().writeValueAsString(shuffleRequest);

        HttpRequest req = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:" + port + "/shuffle"))
                .build();

        HttpResponse<String> res = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());
        ShuffleResponse shuffleResponse = new ObjectMapper()
                .readerFor(ShuffleResponse.class)
                .readValue(res.body());

        ShuffleResponse expected = new ShuffleResponse(Arrays.asList(1, 2, 3, 4, 5));

        assertThat("The responses are not equal", shuffleResponse, TheSameResponseMatcher.isTheSameResponseAs(expected));
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