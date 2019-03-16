package entity;

import ai.AStarPathfinder;
import math.Vector2;
import world.World;

import java.util.Random;

/**
 * A person
 * It moves around the world while avoiding collidable entities
 * It also invokes some special behaviours from intelligent entities
 */
public class Person extends DrawableEntity implements IThinker {
    private transient Vector2[] path; // The path that the person is currently walking
    private transient Vector2 waypoint; // Reference to current waypoint
    private transient int waypointCounter; // If this is a certain value, we need a new path!
    private transient float walkspeed = 1; // anything higher than one is too fast. keep it as the default walkspeed

    public Person(World world) {
        super(world);
        this.spritePath = "/res/sprites/Person.png";
        this.isCollidable = false; // people can walk through each other
    }

    /**
     * The person will take one step towards the Vector2 supplied
     * It will approach the point at a speed relative to its walkspeed
     * @param point The point to step towards
     */
    private void stepTowards(Vector2 point) {
        double dX = point.getX() - getPosition().getX(); // x and y distance
        double dY = point.getY() - getPosition().getY();
        double nL = Math.sqrt(dX*dX + dY*dY); // get magnitude
        dX = dX / nL; // get the orientation to walk in each direction
        dY = dY / nL;
        Vector2 newPos = new Vector2(getPosition().getX() + dX*walkspeed, getPosition().getY() + dY*walkspeed); // take a step towards the previously calculated orientation
        setPosition(newPos.getX(), newPos.getY());
    }

    /**
     * Creates a new path with waypoints that will get the person from their current position to the goal position
     * @param goal The position to move to
     */
    public void setGoal(Vector2 goal) {
        AStarPathfinder pathfinder = new AStarPathfinder(); // a new pathfinder
        Vector2 adjustedPosition = world.worldToGridIndexRounded(getPosition()); // we round it because flooring it gets the wrong ccell
        Vector2 adjustedGoal = world.worldToGridIndex(goal); // convert to grid position. saves memory when pathfinding
        path = pathfinder.findPath(world, adjustedPosition, adjustedGoal); // find a path!

        for (int i = 0; i < path.length; i++) { // for each waypoint...
            Vector2 deflated = path[i];
            // inflate the path to world positions.
            // constant 32 is temporary
            Vector2 inflated = new Vector2(deflated.getX()*32, deflated.getY()*32);
            path[i] = inflated; // replace it
        }

        if (path.length != 0) { // if a path exists...
            path[0] = getPosition(); // set the first point to the persons exact position
            path[path.length-1] = goal; // shift the last point to be the user's actual goal as specified
        }

        waypointCounter = 0; // reset counter
    }

    /**
     * Generates a random integer in a range
     * @param min minimum returned value
     * @param max maximum returned value
     * @return a random integer between min and max
     */
    public int randInt(int min, int max) {
        Random rand = new Random();
        return rand.nextInt((max-min)+1) + min;
    }

    /**
     * Get a random unoccupied position in the world's active building
     * Note that it will also get positions that are unreachable!!
     * @return a random unoccupied point within the building
     */
    public Vector2 getRandomUnoccupiedPosition() {
        boolean[][] map = world.generateCollisionMap(); // generate a new collision map of the world
        Vector2 lep = world.getBuilding().getPolygon().getLeastExtremePoint(); // get the top left point of the building
        Vector2 mep = world.getBuilding().getPolygon().getMostExtremePoint(); // bottom right point of building
        while (true) { // improvement needed
            // fetch a random grid point within the bounds of the building
            Vector2 pos = world.worldToGridIndex(new Vector2(randInt((int) lep.getX(), (int) mep.getX()), randInt((int) lep.getY(), (int) mep.getY())));
            // check it is in the buildings polygon so that we dont try walk through the walls of a jagged building
            if (world.getBuilding().getPolygon().containsPoint(world.gridToWorldPoint(pos)) && !map[(int) pos.getX()][(int) pos.getY()]) {
                return world.gridToWorldPoint(pos); // return grid position
            }
        }
    }


    /**
     * update the person, causing them to take a step towards their goal (if one exists)
     */
    @Override
    public void update() {
        if (world.getBuilding() != null) { // if a building doesnt exist then just stay still.
            // check that a path exists and that there are pending waypoints
            if (path != null && path.length != 0 && !getPosition().almostEquals(path[path.length - 1])) {
                // we use almostequal to prevent the person getting stuck due to movement overcompensation
                if (getPosition().almostEquals(path[waypointCounter])) {
                    waypointCounter++; // increment counter
                    waypoint = path[waypointCounter]; // new waypoint!
                }
                stepTowards(waypoint); // take a step towards waypoint
            } else { // if a path doesnt exist
                setGoal(getRandomUnoccupiedPosition());  // pathfind to a random point in the building
            }
        }

        // to account for rubble on the floor
        // this could probably be generalised
        this.walkspeed = 1; // default ws
        for (Entity entity: world.getEntities()) {
            if (entity instanceof Rubble) {
                // if this person is standing on rubble
                if (world.worldToGridIndexRounded(getPosition()).equals(world.worldToGridIndexRounded(((Rubble) entity).getPosition()))) {
                    this.walkspeed = 0.3f; // they walk really slow
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Person";
    }

    @Override
    public String getDescription() {
        return "Someone who likes to wander aimlessly";
    }
}

