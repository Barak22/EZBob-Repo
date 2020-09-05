package com.ezbob.test.logserviceweb.server.service;

import com.ezbob.test.logserviceweb.api.LoggerService;
import org.springframework.stereotype.Service;

@Service
public class DefaultLoggerService implements LoggerService {

    public void log(String logInput) {
        System.out.println(logInput);
    }
}
