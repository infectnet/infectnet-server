package io.infectnet.server.controller.websocket.code;


import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import io.infectnet.server.controller.engine.EngineConnector;
import io.infectnet.server.controller.utils.error.ErrorConvertibleException;
import io.infectnet.server.controller.websocket.WebSocketController;
import io.infectnet.server.controller.websocket.WebSocketDispatcher;
import io.infectnet.server.controller.websocket.authentication.SessionAuthenticator;
import io.infectnet.server.controller.websocket.exception.AuthenticationNeededException;
import io.infectnet.server.controller.websocket.exception.MalformedMessageException;
import io.infectnet.server.controller.websocket.messaging.Action;
import io.infectnet.server.controller.websocket.messaging.MessageTransmitter;
import io.infectnet.server.controller.websocket.messaging.SocketMessage;
import io.infectnet.server.engine.core.script.generation.CompilationError;
import io.infectnet.server.service.user.UserDTO;
import org.eclipse.jetty.websocket.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * WebSocket controller responsible for handling code uploading and code providing.
 */
public class CodeController implements WebSocketController {

  private static final Logger logger = LoggerFactory.getLogger(CodeController.class);

  private final EngineConnector engineConnector;

  private final Gson gson;

  private final SessionAuthenticator sessionAuthenticator;

  private final MessageTransmitter messageTransmitter;

  public CodeController(EngineConnector engineConnector, Gson gson,
                        SessionAuthenticator sessionAuthenticator,
                        MessageTransmitter messageTransmitter) {
    this.engineConnector = engineConnector;
    this.gson = gson;
    this.sessionAuthenticator = sessionAuthenticator;
    this.messageTransmitter = messageTransmitter;
  }

  @Override
  public void configure(WebSocketDispatcher webSocketDispatcher) {
    webSocketDispatcher.registerOnMessage(Action.PUT_CODE, this::handleNewCodeUpload);
    webSocketDispatcher.registerOnMessage(Action.GET_CODE, this::provideCurrentCode);
  }

  /**
   * Handles client uploading new source code.
   */
  private void handleNewCodeUpload(Session session, String arguments)
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
          .whenComplete(((compilationErrors, throwable) -> {
            if (throwable == null) {
              sendSuccessfulMessage(session, compilationErrors);
            } else {
              sendExceptionMessage(session, new CompilationFailedException(throwable));
            }
          }));

    } else {
      messageTransmitter.transmitException(session, new AuthenticationNeededException());
    }
  }

  /**
   * Handles client requesting its current source code.
   */
  private void provideCurrentCode(Session session, String arguments) throws IOException {
    Optional<UserDTO> user = sessionAuthenticator.verifyAuthentication(session);

    if (user.isPresent()) {
      String source = engineConnector.getSourceCodeForUser(user.get());

      messageTransmitter
          .transmitString(session,
              new SocketMessage<>(Action.OK, new SourceCode(source), SourceCode.class));

    } else {
      messageTransmitter.transmitException(session, new AuthenticationNeededException());
    }
  }

  /**
   * Sends message to the client containing if the uploaded code had syntax errors.
   */
  private void sendSuccessfulMessage(Session session, List<CompilationError> compilationErrors) {
    try {
      messageTransmitter
          .transmitString(session,
              new SocketMessage<>(Action.COMPILATION_RESULTS,
                  new CompilationResults(compilationErrors),
                  CompilationResults.class));
    } catch (IOException e) {
      logger.warn(e.toString());
    }
  }

  /**
   * Sends message to the client if problems occured during compilation process.
   */
  private void sendExceptionMessage(Session session, ErrorConvertibleException e) {
    try {
      messageTransmitter.transmitException(session, e);
    } catch (IOException e1) {
      logger.warn(e.toString());
    }
  }

  private static class SourceCode {
    private final String source;

    public SourceCode(String source) {
      this.source = source;
    }
  }


}
