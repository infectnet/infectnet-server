package io.infectnet.server.controller.token;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.utils.ResponseUtils;
import io.infectnet.server.service.token.TokenDTO;
import io.infectnet.server.service.token.TokenService;

import java.util.Optional;
import spark.Request;
import spark.Response;

/**
 * REST endpoint responsible for token retrieval, creation and deletion.
 */
public class TokenController implements RestController {

  private static final String URL_PATH = "/admin/token/";

  private static final String TOKEN_STRING_PARAMETER_NAME = ":tokenString";

  private TokenService tokenService;

  private Gson gson;

  @Override
  public void configure() {
    get(URL_PATH, this::tokenRetrievalEndpoint, gson::toJson);

    post(URL_PATH, this::tokenCreationEndpoint, gson::toJson);

    delete(URL_PATH + TOKEN_STRING_PARAMETER_NAME, this::tokenDeletionEndpoint);
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

    return ResponseUtils.EMPTY_OK;
  }

  public void setTokenService(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  public void setGson(Gson gson) {
    this.gson = gson;
  }
}
