package ai;

import math.Vector2;
import world.World;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A pathfinding class that can find a path from one GRID POINT to another.
 * Please use sparingly, it's very slow
 * Inspired by a snippet from http://www.cokeandcode.com/main/tutorials/path-finding/
 */
public class AStarPathfinder {
    private static final int MAX_ITERATIONS = 1000; // A lazy method of preventing random crashes

    /**
     * An abstraction over the Vector2 class, contains metadata about a position in the world relative to the path.
     */
    private class Node {
        Node parent = null;
        Vector2 position = null;

        double g = 0;
        double h = 0;
        double f = 0;

        /**
         * Abstraction over Vector2.equals
         * @param node The other node to check
         * @return Whether or not the local and other node are equal
         */
        boolean isEqual(Node node) {
            return (position.equals(node.position));
        }
    }

    public Vector2[] findPath(World world, Vector2 start, Vector2 end) {
        int counter = 0;

        boolean[][] collisionMap = world.generateCollisionMap();
        if (collisionMap[(int) start.getX()][(int) start.getY()]) return (new Vector2[0]);

        Node startNode = new Node();
        startNode.position = start;

        Node goalNode = new Node();
        goalNode.position = end;

        ArrayList<Node> openList = new ArrayList<>();
        openList.add(startNode);

        ArrayList<Node> closedList = new ArrayList<>();

        while (openList.size() > 0) {
            counter++;
            if (counter > MAX_ITERATIONS) { // Something has gone horribly wrong!
                return (new Vector2[0]); // Return an empty path
            }
            Node currentNode = openList.get(0);
            int currentIndex = 0;
            for (int i = 0; i < openList.size(); i++) {
                Node selectedNode = openList.get(i);
                if (selectedNode.f < currentNode.f) {
                    currentNode = selectedNode;
                    currentIndex = i;
                }
            }

            openList.remove(currentIndex);
            closedList.add(currentNode);

            if (currentNode.isEqual(goalNode)) {
                ArrayList<Vector2> path = new ArrayList<>();
                Node selectedNode = currentNode;
                while (selectedNode != null) {
                    path.add(selectedNode.position);
                    selectedNode = selectedNode.parent;
                }
                Collections.reverse(path);
                return path.toArray(new Vector2[0]);
            }

            ArrayList<Node> childNodes = new ArrayList<>();

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0) continue;
                    Vector2 position = new Vector2(currentNode.position.getX() + x, currentNode.position.getY() + y);
                    if (position.getX() < 0 || position.getX() > collisionMap[0].length-1 || position.getY() < 0 || position.getY() > collisionMap.length-1) continue;
                    if (collisionMap[(int) position.getX()][(int) position.getY()]) continue;
                    Node newNode = new Node();
                    newNode.parent = currentNode;
                    newNode.position = position;
                    childNodes.add(newNode);
                }
            }

            for (Node child : childNodes) {
                for (Node node : closedList)  if (child.isEqual(node)) continue;
                child.g = currentNode.g + 1;
                child.h = Math.sqrt(Math.pow(child.position.getX() - goalNode.position.getX(), 2) + Math.pow(child.position.getY() - goalNode.position.getY(), 2));
                child.f = child.g + child.h;
                for (Node selectedNode : openList) if (child.isEqual(selectedNode) && child.g > selectedNode.g) continue;
                openList.add(child);
            }
        }

        return null;
    }
}
