package io.infectnet.server.engine.core.world.strategy;

import io.infectnet.server.engine.core.world.Position;
import io.infectnet.server.engine.core.world.Tile;
import io.infectnet.server.engine.core.world.World;

import java.util.*;

import static io.infectnet.server.engine.core.world.TileType.CAVE;

/**
 * Class for Path finding in the world between two Positions, using the A* algorithm.
 */
public class AStarPathFinderStrategy implements PathFinderStrategy{

    /**
     * A heuristic to use with value calculating.
     */
    private final Heuristic heuristic;

    /**
     * The costs of stepping to a Tile with a defined TileType.
     */
    private static final int COST_OF_CAVE = 1;

    private static final int COST_OF_ROCK = 100;

    private static final int DEFAULT_COST = 0;

    /**
     * Creates a startegy with the given heuristic to use in the A* algorithm.
     * @param heuristic the specific heuristic function to use
     */
    public AStarPathFinderStrategy(Heuristic heuristic){
        this.heuristic = heuristic;
    }

    @Override
    public LinkedList<Tile> findPath(World world, Position start, Position target) {

        /*  The list of Nodes that we do not yet consider fully searched. */

        Set<Node> openNodes = new HashSet<>();

        /* The list of nodes that have been searched through. */

        Set<Node> closedNodes = new HashSet<>();

        /* Finding the right target, if the given is a occupied by an Entity,
        which is almost always e.g. resource, other player's entity. */

        Position targetPos = resetTarget(world, target);

        /* Putting the starting node with its parameters in the list of open Nodes,
         to expand it later on. */

        Node startNode = new Node(start);
        startNode.setCost(0);
        startNode.setHeuristic(heuristic.heuristic(world, start, targetPos));
        startNode.setSumCost(startNode.getHeuristic());

        openNodes.add(startNode);

        /* The current Node represents the current end of the found path so far. */

        Node current = null;

        /* The loop goes as long as there are nodes to expand
        or we were able to find a path to the target. */

        while(!openNodes.isEmpty()){

            /* The current node should be chosen from the openNodes list. */

            current = findNodeToExpand(openNodes);

            /* If we got to the desired target, the algorithm stops. */

            if(current.getPosition().equals(targetPos)){
                break;
            }

            /* Before we expand the cosen Node named as current,
            we switch its formed place in the open list to a place in the closed nodes list. */

            openNodes.remove(current);
            closedNodes.add(current);

            /* Expanding the current node */

            expandNode(world, openNodes, closedNodes, targetPos, current);
        }
        /* Even if no path found to the target Position,
         the path that has already been constructed in the direction of the target will be returned. */

        return getPathFromNodes(world, start, current);

    }

    /**
     *Expanding the previously chosen {@code current} Node into its neighbours.
     * @param world the world to search in
     * @param openNodes the Set of nodes to visit later
     * @param closedNodes the set of already visited nodes
     * @param targetPos the Position of the target we want to reach
     * @param current the currently visited node
     */
    private void expandNode(World world, Set<Node> openNodes, Set<Node> closedNodes, Position targetPos, Node current) {
        for (Node neighbour : current.getNeighbours()) {
            if (neighbour == null) {
                continue;
            }

            /* Computing the cost of the current Node, and comparing with its neighbour's cost,
             to decide where to continue the path building.*/

            int nextCost = current.getCost() + calculateCostOfTile(world, neighbour);

            if (nextCost < neighbour.getCost()) {
                openNodes.remove(neighbour);
                closedNodes.remove(neighbour);
            }

            /* The only case where all conditions are met */

            if (isSuitableNode(world, openNodes, closedNodes, neighbour)) {

                neighbour.setCost(nextCost);
                neighbour.setHeuristic(heuristic.heuristic(world, neighbour.getPosition(), targetPos));
                neighbour.setSumCost(neighbour.getCost() + neighbour.getHeuristic());
                neighbour.setParent(current);
                openNodes.add(neighbour);
            }
        }
    }

    /**
     * Checks if the node given might possibly be the next Node to continue building the path forward,
     * with checking if it is not yet visited before, and whether the Tile defined by the Node's Position
     * is empty and is of type {@code CAVE}.
     * @param world the world to search in
     * @param openNodes the Set of nodes to visit later
     * @param closedNodes the set of already visited nodes
     * @param neighbour the node to check
     * @return true if all conditions are met, false otherwise
     */
    private boolean isSuitableNode(World world, Set<Node> openNodes, Set<Node> closedNodes, Node neighbour) {
        return !openNodes.contains(neighbour)
                && !closedNodes.contains(neighbour)
                && world.getTileByPosition(neighbour.getPosition()).getType() == CAVE
                && world.getTileByPosition(neighbour.getPosition()).getEntity() == null;
    }

    /**
     * Calculating the cost of a Tile, could be used for more than two Tile types,
     * in this case with only one type to move on,  a constant function is enough.
     * @param world the world where we search
     * @param neighbour the node representing  of the Tile given
     * @return the cost of stepping to the given Position
     */
    private int calculateCostOfTile(World world, Node neighbour) {
        Tile tile = world.getTileByPosition(neighbour.getPosition());
        switch (tile.getType()){
            case CAVE: return COST_OF_CAVE;
            case ROCK: return COST_OF_ROCK;
            default: return DEFAULT_COST;
        }
    }

    /**
     * Constructs the Path, which can be returned as the result of the A* algorithm.
     * Usis recursive path building, going from the last Position in the Path, bindig
     * its parent Node to it, and so on. Until it reaches the starting Position.
     * @param world the world where we search
     * @param start the starting Position
     * @param current the end Position of the current Path, may not be the original target
     * @return a linked list of the path containing the target at the front and the start at the back
     */
    private LinkedList<Tile> getPathFromNodes(World world, Position start, Node current) {
        LinkedList<Tile> path = new LinkedList<>();
        Node node = current;

        while (node.getParent() != null) {
            path.add(world.getTileByPosition(node.getPosition()));
            node = node.getParent();
        }
        path.add(world.getTileByPosition(start));
        return path;
    }

    /**
     * Finds the next Node to expand, which have to have the lowest cost.
     * @param openNodes the Set of nodes to visit later
     * @return the chosen Node
     */
    private Node findNodeToExpand(Set<Node> openNodes) {
        Node current = null;

        for (Node node : openNodes) {
            if (current == null || node.getSumCost() < current.getSumCost()) {
                current = node;
            }
        }

        return current;
    }

    /**
     * Always repositions the given target Position to one, that is not occupied or not a {@code ROCK}.
     * @return the new position nearby the original target
     */
    private Position resetTarget(World world, Position target) {
        Position newTarget =  target;
        Node targetNode = new Node(target);

        for(Node node : targetNode.getNeighbours()){
            if(world.isPositionValidTile(node.getPosition())
                    && !world.getTileByPosition(node.getPosition()).isBlockedOrOccupied()){
                newTarget = node.getPosition();
                break;
            }
        }
        return newTarget;
    }

    /**
     * Inner class to represent the special cell required by the A* algorithm
     */
    private static class Node {
        private Node parent;
        private Position position;
        private int cost;
        private int heuristic;
        private int SumCost;

        Node(Position position) {
            this.position = position;
        }

        /**
         * Creates all neighbouring Nodes of the current Node.
         * @return a list of Nodes
         */
        List<Node> getNeighbours() {
            List<Node> nodes = new ArrayList<>();
            nodes.add(new Node(this.getPosition().stepNorth()));
            nodes.add(new Node(this.getPosition().stepNorthEast()));
            nodes.add(new Node(this.getPosition().stepEast()));
            nodes.add(new Node(this.getPosition().stepSouthEast()));
            nodes.add(new Node(this.getPosition().stepSouth()));
            nodes.add(new Node(this.getPosition().stepSouthWest()));
            nodes.add(new Node(this.getPosition().stepWest()));
            nodes.add(new Node(this.getPosition().stepNorthWest()));
            return nodes;
        }

        Node getParent() {
            return parent;
        }

        void setParent(Node parent) {
            this.parent = parent;
        }

        Position getPosition() {
            return position;
        }

        int getCost() {
            return cost;
        }

        void setCost(int cost) {
            this.cost = cost;
        }

        int getHeuristic() {
            return heuristic;
        }

        void setHeuristic(int heuristic) {
            this.heuristic = heuristic;
        }

        int getSumCost() {
            return SumCost;
        }

        void setSumCost(int sumCost) {
            SumCost = sumCost;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Node node = (Node) o;

            return getPosition() != null ? getPosition().equals(node.getPosition())
                    : node.getPosition() == null;

        }

        @Override
        public int hashCode() {
            int result = (getPosition() != null ? getPosition().hashCode() : 0);
            return result;
        }
    }
}
