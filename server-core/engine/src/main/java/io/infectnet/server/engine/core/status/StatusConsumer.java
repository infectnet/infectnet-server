package io.infectnet.server.engine.core.status;

import io.infectnet.server.engine.core.player.Player;

import java.util.function.BiConsumer;

@FunctionalInterface
public interface StatusConsumer extends BiConsumer<Player, StatusMessage> {


}
