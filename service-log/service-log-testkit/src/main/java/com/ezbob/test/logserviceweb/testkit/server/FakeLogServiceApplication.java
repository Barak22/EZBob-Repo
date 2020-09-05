package com.ezbob.test.logserviceweb.testkit.server;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class FakeLogServiceApplication {
    public static String portEnv = "server.port";

    public void start(String[] args) {
        String port = System.getenv(portEnv);
        Map<String, Object> prop = new HashMap<>();
        prop.put(portEnv, port);
        new SpringApplicationBuilder()
                .properties(prop)
                .sources(FakeLogServiceApplication.class)
                .run(args);
    }

}
