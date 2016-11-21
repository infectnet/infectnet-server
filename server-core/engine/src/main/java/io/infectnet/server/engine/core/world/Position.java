package io.infectnet.server.engine.core.world;

/**
 * A class to hold two coordinates together, which define an exact {@link Tile} in the {@link World}.
 */
public class Position{
    /**
     * The first component, referring to height.
     */
    private final int h;

    /**
     * The second component, referring to width.
     */
    private final int w;

    /**
     * Creates a {@code Position} with the given coordinates.
     * @param h the first component
     * @param w the second component
     */
    Position(int h, int w){
        this.h = h;
        this.w = w;
    }

    /**
     * Creates this Position's neighbour, stepping one step south.
     * @return the southern neighbour
     */
    Position stepSouth(){
        return new Position(h+1, w);
    }

    /**
     * Creates this Position's neighbour, stepping one step west.
     * @return the western neighbour
     */
    Position stepWest(){
        return new Position(h, w-1);
    }

    /**
     * Creates this Position's neighbour, stepping one step north.
     * @return the northern neighbour
     */
    Position stepNorth(){
        return new Position(h-1, w);
    }

    /**
     * Creates this Position's neighbour, stepping one step east.
     * @return the eastern neighbour
     */
    Position stepEast(){
        return new Position(h, w+1);
    }

    int getH() {
        return h;
    }

    int getW() {
        return w;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (h != position.h) return false;
        return w == position.w;

    }

    @Override
    public int hashCode() {
        int result = h;
        result = 31 * result + w;
        return result;
    }
}
