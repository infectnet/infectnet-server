package io.infectnet.server.engine.core.status;

/**
 * Interface for sending out world updates to players.
 */
public interface StatusPublisher {

  /**
   * Publishes world updates specified for each player.
   * @param statusConsumer the consumer to use to send out {@link StatusMessage}s
   */
  void publish(StatusConsumer statusConsumer);

}
