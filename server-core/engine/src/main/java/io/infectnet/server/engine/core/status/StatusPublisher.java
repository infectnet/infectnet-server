package io.infectnet.server.engine.core.status;


public interface StatusPublisher {

  void publish(StatusConsumer statusConsumer);

}
