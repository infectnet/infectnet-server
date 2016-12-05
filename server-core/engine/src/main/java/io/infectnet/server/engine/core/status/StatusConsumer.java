package io.infectnet.server.engine.core.status;

import io.infectnet.server.engine.core.player.Player;

import java.util.function.BiConsumer;

/**
 * Consumer specialization that accepts a {@link Player} and a {@link StatusMessage} instance. Can
 * be used to consume and process status updates for players.
 */
@FunctionalInterface
public interface StatusConsumer extends BiConsumer<Player, StatusMessage> {

}
