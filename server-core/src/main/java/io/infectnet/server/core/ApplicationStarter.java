package io.infectnet.server.core;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.user.RegistrationController;
import io.infectnet.server.controller.token.TokenController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class ApplicationStarter {

  private static final Logger logger = LoggerFactory.getLogger(ApplicationStarter.class);

  private final List<RestController> restControllers;

  @Inject
  public ApplicationStarter() {
    // Notice that there is an Inject annotation even if there are no dependencies!

    restControllers = Arrays.asList(
        new RegistrationController(),
        new TokenController()
    );
  }

  public void start() {

    for (RestController controller : restControllers) {
      controller.configure();
    }

    logger.info("Controllers configured!");

  }

}
