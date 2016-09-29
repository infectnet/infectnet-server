package io.infectnet.server.core;

import io.infectnet.server.controller.impl.RegistrationController;
import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.impl.TokenController;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class ApplicationStarter {

    List<RestController> restControllers = Arrays.asList(
            new RegistrationController(),
            new TokenController()
    );

    @Inject
    public ApplicationStarter() {
        // This constructor is needed for Dagger
        // Notice that there is an Inject annotation even if there are no dependencies!
    }

    public void start() {
        System.out.println("Application started!");

        for (RestController controller : restControllers) {
            controller.configure();
        }


    }

}
