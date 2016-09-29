package io.infectnet.server.core;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.impl.RegistrationController;
import io.infectnet.server.controller.impl.TokenController;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class ApplicationStarter {

    private List<RestController> restControllers;

    @Inject
    public ApplicationStarter() {
        // Notice that there is an Inject annotation even if there are no dependencies!

        restControllers = Arrays.asList(
                new RegistrationController(),
                new TokenController()
        );
    }

    public void start() {
        System.out.println("Application started!");

        for (RestController controller : restControllers) {
            controller.configure();
        }


    }

}
