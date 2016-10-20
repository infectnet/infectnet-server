package io.infectnet.server.controller.token;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;

import io.infectnet.server.controller.RestController;
import io.infectnet.server.controller.utils.ResponseUtils;
import io.infectnet.server.service.token.TokenDTO;
import io.infectnet.server.service.token.TokenService;

import java.util.List;
import java.util.Optional;

/**
 * REST endpoint responsible for token retrieval, creation and deletion.
 */
public class TokenController implements RestController {

  private TokenService tokenService;

  @Override
  public void configure() {
    createTokenRetrievalEndpoint();
    createTokenCreationEndpoint();
    createTokenDeletionEndpoint();
  }

  private void createTokenRetrievalEndpoint() {
    get("/admin/token", (req, resp) -> {
      List<TokenDTO> tokenList = tokenService.getAllTokens();

      return tokenList.toString();
    });
  }

  private void createTokenCreationEndpoint() {
    post("/admin/token", (req, resp) -> tokenService.createNewToken());
  }

  private void createTokenDeletionEndpoint() {
    delete("/admin/token/:tokenString", (req, resp) -> {
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
}
