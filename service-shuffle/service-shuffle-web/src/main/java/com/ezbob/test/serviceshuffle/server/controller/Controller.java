package com.ezbob.test.serviceshuffle.server.controller;

import com.ezbob.test.serviceshuffle.api.ShuffleRequest;
import com.ezbob.test.serviceshuffle.api.ShuffleResponse;
import com.ezbob.test.serviceshuffle.server.service.ShuffleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
class Controller {
    private ShuffleService service;


    public Controller(ShuffleService service) {
        this.service = service;
    }

    @PostMapping("/shuffle")
    public ShuffleResponse generateArray(@RequestBody ShuffleRequest req) {
        List<Integer> list = service.generateRandomArray(req.getNumberForRandomArray());
        return new ShuffleResponse(list);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }
}
