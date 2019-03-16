package world;

import entity.EntityGroup;
import math.Polygon;
import math.Vector2;

import java.io.Serializable;

/**
 * A room that can be contained within the world
 * Unlike a building, it counts the number of occupants within it
 */
public class Room implements Serializable {
    EntityGroup room = new EntityGroup();

    Polygon polygon; // the polygon denoting the walls of the room
    World world; // world it is contained within

    private int numEntities = 0; // the number of entities within the room
    private int numOccupants = 0; // the number of people within the room

    /**
     * initilise the room
     * @param vertices the vertices of the polygon associated with the walls of the room
     * @param doorPoint the position of the door, ideally this should be intersecting with the wall
     * @param world the world to add the room to
     */
    Room(Vector2[] vertices, Vector2 doorPoint, World world) {
        Polygon polygon = new Polygon(); // create a new polygon with the given vertices
        polygon.setVertices(vertices);
        this.polygon = polygon;
        this.world = world;
        room.combine(world.getGenerationHelper().generateRoom(vertices, doorPoint)); // invoke world's generation helper to create the tiles of the room
    }

    /**
     * get the number of occupants within the room
     * @return the number of occupants within the room
     */
    public int getNumOccupants() {
        return numOccupants;
    }

    public void setNumOccupants(int num) {
        this.numOccupants = num;
    }
}
