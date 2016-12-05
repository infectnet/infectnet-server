package io.infectnet.server.engine.core.status;

import java.util.function.Consumer;

@FunctionalInterface
public interface StatusConsumer extends Consumer<StatusMessage> {


}
