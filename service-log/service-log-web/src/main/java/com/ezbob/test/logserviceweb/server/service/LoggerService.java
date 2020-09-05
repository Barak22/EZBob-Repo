package com.ezbob.test.logserviceweb.server.service;

import org.springframework.stereotype.Service;

@Service
public class LoggerService {

    public void log(String logInput) {
        System.out.println(logInput);
    }
}
