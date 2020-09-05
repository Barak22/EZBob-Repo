package com.ezbob.test.logserviceweb.server.controller;

import com.ezbob.test.logserviceweb.api.LogRequest;
import com.ezbob.test.logserviceweb.api.LogResponse;
import com.ezbob.test.logserviceweb.server.service.LoggerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceLogController {

    private LoggerService loggerService;

    public ServiceLogController(LoggerService loggerService) {
        this.loggerService = loggerService;
    }

    @PostMapping("/log")
    public LogResponse log(@RequestBody LogRequest req) {
        loggerService.log(req.getLogInput());
        return new LogResponse(true);
    }

}
