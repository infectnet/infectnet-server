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

/**
 * REST endpoint responsible for token retrieval, creation and deletion.
 */
public class TokenController implements RestController {

  private static final String URL_PATH = "/admin/token";

  private TokenService tokenService;

  private Gson gson;

  @Override
  public void configure() {
    createTokenRetrievalEndpoint();
    createTokenCreationEndpoint();
    createTokenDeletionEndpoint();
  }

  private void createTokenRetrievalEndpoint() {
    get(URL_PATH, (req, resp) -> tokenService.getAllTokens(), gson::toJson);
  }

  private void createTokenCreationEndpoint() {
    post(URL_PATH, (req, resp) -> tokenService.createNewToken(), gson::toJson);
  }

  private void createTokenDeletionEndpoint() {
    delete(URL_PATH + "/:tokenString", (req, resp) -> {
      Optional<TokenDTO> token = tokenService.getTokenByTokenString(req.params(":tokenString"));

      if (token.isPresent()) {
        tokenService.delete(token.get());
      }

      return ResponseUtils.EMPTY_OK;
    });
  }

  public void setTokenService(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  public void setGson(Gson gson) {
    this.gson = gson;
  }
}
