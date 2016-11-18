package io.infectnet.server.controller.websocket.game;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import io.infectnet.server.controller.engine.EngineConnector;
import io.infectnet.server.controller.error.ErrorConvertibleException;
import io.infectnet.server.controller.websocket.authentication.SessionAuthenticator;
import io.infectnet.server.controller.websocket.exception.AuthenticationNeededException;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import io.infectnet.server.controller.websocket.messaging.Action;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitter;
import io.infectnet.server.controller.websocket.messaging.SocketMessage;
import io.infectnet.server.service.user.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class GameController {

  private final EngineConnector engineConnector;

  private final Gson gson;

  private final SessionAuthenticator sessionAuthenticator;

  private final MessageTransmitter messageTransmitter;

  public GameController(EngineConnector engineConnector, Gson gson,
                        SessionAuthenticator sessionAuthenticator,
                        MessageTransmitter messageTransmitter) {
    this.engineConnector = engineConnector;
    this.gson = gson;
    this.sessionAuthenticator = sessionAuthenticator;
    this.messageTransmitter = messageTransmitter;
  }

  public void handleNewCodeUpload(Session session, String arguments)
      throws MalformedMessageException, IOException {

    Optional<UserDTO> user = sessionAuthenticator.verifyAuthentication(session);

    if (user.isPresent()) {
      SourceCode sourceCode;

      try {
        sourceCode = gson.fromJson(arguments, SourceCode.class);

        Objects.requireNonNull(sourceCode.source);
      } catch (JsonParseException | NullPointerException e) {
        throw new MalformedMessageException(e);
      }

      engineConnector.compileAndUploadForUser(user.get(), sourceCode.source)
          .whenComplete(((aVoid, throwable) -> {
            if (throwable == null) {
              try {
                sendSuccessfulMessage(session);
              } catch (IOException e) {
                //TODO
                e.printStackTrace();
              }
            } else {
              try {
                sendExceptionMessage(session, new CompileFailedException(throwable));
              } catch (IOException e) {
                //TODO
                e.printStackTrace();
              }
            }
          }));

    } else {
      messageTransmitter.transmitException(session, new AuthenticationNeededException());
    }

  }

  private void sendExceptionMessage(Session session, ErrorConvertibleException e)
      throws IOException {
    messageTransmitter.transmitException(session, e);
  }

  private void sendSuccessfulMessage(Session session) throws IOException {
    messageTransmitter
        .transmitString(session, new SocketMessage<>(Action.OK, StringUtils.EMPTY, String.class));
  }

  private static class SourceCode {
    private final String source;

    public SourceCode(String source) {
      this.source = source;
    }
  }


}
