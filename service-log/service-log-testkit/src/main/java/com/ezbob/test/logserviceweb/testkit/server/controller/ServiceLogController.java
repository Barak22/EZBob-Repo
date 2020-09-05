package com.ezbob.test.logserviceweb.testkit.server.controller;

import com.ezbob.test.logserviceweb.api.LogRequest;
import com.ezbob.test.logserviceweb.api.LogResponse;
import com.ezbob.test.logserviceweb.testkit.server.service.FakeLoggerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceLogController {

    private FakeLoggerService loggerService;

    public ServiceLogController(FakeLoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @PostMapping("/log")
    public LogResponse log(@RequestBody LogRequest req) {
        loggerService.log(req.getLogInput());
        return new LogResponse(true);
    }

}
