package io.infectnet.server.controller.rest.user;

import static spark.Spark.get;

import com.google.gson.Gson;

import io.infectnet.server.controller.rest.RestController;
import io.infectnet.server.service.user.UserService;

import spark.Request;
import spark.Response;

/**
 * Controller that exposes an endpoint to list the registered users.
 */
public class UserListingController implements RestController {
  private static final String URL_PATH = "/admin/users";

  private final UserService userService;

  private final Gson gson;

  public UserListingController(UserService userService, Gson gson) {
    this.userService = userService;

    this.gson = gson;
  }

  @Override
  public void configure() {
    get(URL_PATH, this::userListingEndpoint, gson::toJson);
  }

  private Object userListingEndpoint(Request req, Response resp) {
    return userService.getAllUsers();
  }
}
