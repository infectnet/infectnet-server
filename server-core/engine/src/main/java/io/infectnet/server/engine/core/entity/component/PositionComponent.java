package io.infectnet.server.engine.core.entity.component;

import io.infectnet.server.engine.core.world.Position;

/**
 * Component storing an {@link io.infectnet.server.engine.core.entity.Entity}'s position in the world.
 */
public class PositionComponent {

    private Position position;

    public PositionComponent(){
        this.position = null;
    }

    public PositionComponent(Position position){
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
