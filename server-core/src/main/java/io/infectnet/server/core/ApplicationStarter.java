package io.infectnet.server.core;

import javax.inject.Inject;

public class ApplicationStarter {

    @Inject
    public ApplicationStarter() {
        // This constructor is needed for Dagger
        // Notice that there is an Inject annotation even if there are no dependencies!
    }

    public void start() {
        System.out.println("Application started!");
    }

}
