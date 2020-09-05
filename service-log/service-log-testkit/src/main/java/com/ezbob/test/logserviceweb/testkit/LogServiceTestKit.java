package com.ezbob.test.logserviceweb.testkit;

import com.ezbob.test.logserviceweb.testkit.server.FakeLogServiceApplication;

public class LogServiceTestKit {

    private static FakeLogServiceApplication serviceApplication = new FakeLogServiceApplication();

    public static void start() {
        serviceApplication.start(new String[]{});
    }

    public static void setPort(int port) {
        System.setProperty(FakeLogServiceApplication.portEnv, String.valueOf(port));
    }
}
