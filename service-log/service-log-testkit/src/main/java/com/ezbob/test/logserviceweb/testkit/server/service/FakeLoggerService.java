package com.ezbob.test.logserviceweb.testkit.server.service;

import com.ezbob.test.logserviceweb.api.LoggerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeLoggerService implements LoggerService {

    private static List<String> logs = new ArrayList<>();

    public void log(String logInput) {
        logs.add(logInput);
    }

    static public List<String> getLogsList() {
        return logs;
    }
}
