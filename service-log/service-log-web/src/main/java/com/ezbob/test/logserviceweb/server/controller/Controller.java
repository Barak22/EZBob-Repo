package com.ezbob.test.logserviceweb.server.controller;

import com.ezbob.test.logserviceweb.api.LogRequest;
import com.ezbob.test.logserviceweb.api.LogResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping("/log")
    public LogResponse log(@RequestBody LogRequest req) {
        return new LogResponse("OK");
    }

}
