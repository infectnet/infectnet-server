package io.infectnet.server.controller.token;

import static io.infectnet.server.controller.utils.ResponseUtils.sendEmptyOk;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.service.token.TokenDTO;
import io.infectnet.server.service.token.TokenService;

import java.util.Optional;
import spark.Request;
import spark.Response;

/**
 * REST endpoint responsible for token retrieval, creation and deletion.
 */
public class TokenController implements RestController {

  private static final String URL_PATH = "/admin/tokens";

  private static final String TOKEN_STRING_PARAMETER_NAME = ":tokenString";

  private final TokenService tokenService;

  private final Gson gson;

  /**
   * Constructs a new controller providing token related endpoints using the given token service.
   * @param tokenService the token service to be used
   * @param gson the JSON transformer to be used
   */
  public TokenController(TokenService tokenService, Gson gson) {
    this.tokenService = tokenService;
    this.gson = gson;
  }

  @Override
  public void configure() {
    get(URL_PATH, this::tokenRetrievalEndpoint, gson::toJson);

    post(URL_PATH, this::tokenCreationEndpoint, gson::toJson);

    delete(URL_PATH + "/" + TOKEN_STRING_PARAMETER_NAME, this::tokenDeletionEndpoint);
  }

  private Object tokenRetrievalEndpoint(Request req, Response resp) {
    return tokenService.getAllTokens();
  }

  private Object tokenCreationEndpoint(Request req, Response resp) {
    return tokenService.createNewToken();
  }

  private Object tokenDeletionEndpoint(Request req, Response resp) {
    Optional<TokenDTO>
        token =
        tokenService.getTokenByTokenString(req.params(TOKEN_STRING_PARAMETER_NAME));

    if (token.isPresent()) {
      tokenService.delete(token.get());
    }

    return sendEmptyOk(resp);
  }

}
