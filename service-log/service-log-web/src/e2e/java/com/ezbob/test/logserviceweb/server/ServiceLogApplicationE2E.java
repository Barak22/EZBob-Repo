package com.ezbob.test.logserviceweb.server;

import com.ezbob.test.logserviceweb.api.LogRequest;
import com.ezbob.test.logserviceweb.api.LogResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.ezbob.test.logserviceweb.server.TheSameResponseMatcher.isTheSameResponseAs;
import static org.junit.Assert.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ServiceLogApplicationE2E {

    @LocalServerPort
    int port;

    @Test
    void logMessage() throws IOException, InterruptedException {
        LogRequest logRequest = new LogRequest("stamLog");
        String body = requestAsString(logRequest);

        LogResponse actualLogResponse = executeRequestWith(body);

        LogResponse expectedLogResponse = new LogResponse(true);

        assertThat("The responses are not equal",
                actualLogResponse,
                isTheSameResponseAs(expectedLogResponse));
    }

    private String requestAsString(LogRequest logRequest) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(logRequest);
    }

    private LogResponse executeRequestWith(String body) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .header("Content-Type", "application/json")
                .uri(URI.create("http://localhost:" + port + "/log"))
                .build();

        HttpResponse<String> res = HttpClient.newHttpClient().send(req, HttpResponse.BodyHandlers.ofString());
        return new ObjectMapper()
                .readerFor(LogResponse.class)
                .readValue(res.body());
    }
}

class TheSameResponseMatcher extends TypeSafeMatcher<LogResponse> {

    LogResponse expected;

    public TheSameResponseMatcher(LogResponse expected) {
        this.expected = expected;
    }

    @Override
    protected boolean matchesSafely(LogResponse actual) {
        return expected.equals(actual);
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(expected.toString());
    }

    public static Matcher<LogResponse> isTheSameResponseAs(LogResponse expected) {
        return new TheSameResponseMatcher(expected);
    }
}