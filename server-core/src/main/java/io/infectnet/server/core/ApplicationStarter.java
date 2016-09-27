package io.infectnet.server.core;

import javax.inject.Inject;

public class ApplicationStarter {

    @Inject
    public ApplicationStarter() {
    }

    public void start() {
        System.out.println("Application started!");
    }

}
