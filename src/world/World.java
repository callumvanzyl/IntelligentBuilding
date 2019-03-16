package world;

import ai.AStarPathfinder;
import entity.DrawableEntity;
import entity.Entity;
import entity.EntityGroup;
import entity.Person;
import game.Game;
import math.Polygon;
import math.Vector2;
import tile.TileManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * a core class that contains all of the entities and context relating to the world
 * this object is saved and loaded to allow for retrieval of worlds
 * it also contains references to shared managers and a generation helper
 */
public class World implements Serializable {
    private EntityGroup entityGroup = new EntityGroup(); // contains all of the entities in the world

    private transient Game game; // the game context associated with this world
    private transient GenerationHelper generationHelper; // a tile generation helper
    private transient TileManager tileManager; // shared tile manager
    private transient int tileSize; // tile size

    private Building building; // the current building
    private ArrayList<Room> rooms = new ArrayList<>(); // an array containing references to all rooms in the world

    public World(Game game) {
        setGame(game);
    }

    /**
     * add a drawable entity to the world
     * @param entity the entity to add to the world
     */
    public void addEntity(DrawableEntity entity){
        entityGroup.add(entity);
    }

    /**
     * add an ordinary entity to the world
     * @param entity the entity to add to the world
     */
    public void addEntity(Entity entity){
        entityGroup.add(entity);
    }

    /**
     * create a new building and associate it with this world
     * @param vertices the vertices of the building walls
     */
    public void createBuilding(Vector2[] vertices) {
        building = new Building(vertices, this);
    }

    /**
     * create a new room and append it to this worlds list of rooms
     * @param vertices the vertices of the room walls
     * @param doorPoint the position of the room door
     */
    public void createRoom(Vector2[] vertices, Vector2 doorPoint) {
        Room room = new Room(vertices, doorPoint, this); // invoke generator to generate structure
        rooms.add(room);
    }

    public void createGrassyPlain(Vector2 size) {
        Polygon grass = new Polygon();
        grass.appendVertex(new Vector2(0, 0));
        grass.appendVertex(new Vector2(size.getX()*tileSize, 0));
        grass.appendVertex(new Vector2(size.getX()*tileSize, size.getY()*tileSize));
        grass.appendVertex(new Vector2(0, size.getY()*tileSize));
        grass.appendVertex(new Vector2(0, 0));

        generationHelper.fillPolygonWithTile(grass, (byte) 0, 0);
    }

    public boolean[][] generateCollisionMap() {
        ArrayList<DrawableEntity> collidable = new ArrayList<>();

        Vector2 max = new Vector2();
        for (Entity entity: entityGroup.getEntities()) {
            if (entity instanceof DrawableEntity) {
                DrawableEntity temp = (DrawableEntity) entity;
                if (entity.getIsCollidable())
                    collidable.add(temp);
                if (temp.getPosition().getX() > max.getX())
                    max.setX(temp.getPosition().getX());
                if (temp.getPosition().getY() > max.getY())
                    max.setY(temp.getPosition().getY());
            }
        }

        boolean[][] map = new boolean[(int) max.getX()/tileSize][(int) max.getY()/tileSize];
        for (boolean[] booleans : map) Arrays.fill(booleans, false);
        collidable.stream().map(entity -> worldToGridIndex(entity.getPosition())).forEach(gridPos -> map[(int) gridPos.getX()][(int) gridPos.getY()] = true);

        return map;
    }

    public Room getRoomAtPoint(Vector2 point) {
        for(Room room: rooms) {
            if (room.polygon.containsPoint(point)) {
                return room;
            }
        }
        return null;
    }

    /**
     * inflate points from a grid to world reference
     * @param point the point to inflate
     * @return an inflated point
     */
    public Vector2 gridToWorldPoint(Vector2 point) {
        return new Vector2(tileSize*point.getX(), tileSize*point.getY()); // multiply grid index by tilesize to get world point
    }

    /**
     * deflate a point from world to grid reference
     * @param point the point to deflate
     * @return a deflated point
     */
    public Vector2 worldToGridIndex(Vector2 point) {
        return new Vector2(Math.floor(point.getX()/tileSize), Math.floor(point.getY()/tileSize)); // divide by tilesize and floor to deflate point
    }

    /**
     * similar to worldtogridindex, except the point is rounded instead of floored
     * @param point the point to deflate
     * @return a deflated point
     */
    public Vector2 worldToGridIndexRounded(Vector2 point) {
        return new Vector2(Math.round(point.getX()/tileSize), Math.round(point.getY()/tileSize)); // divide by tilesize and round the deflated point
    }

    /**
     * convert to grid index, then inflate
     * @param point the point to convert
     * @return an inflated point
     */
    public Vector2 worldToGridPoint(Vector2 point) {
        Vector2 asGridIndex = worldToGridIndex(point); // deflate to grid index
        return new Vector2(tileSize*asGridIndex.getX(), tileSize*asGridIndex.getY()); // multiply by tile size to inflate
    }

    public void update() {
        for (Room room: rooms) {
            int i = 0;
            for (Entity entity : entityGroup.getEntities()) {
                if (entity instanceof Person) {
                    if (room.polygon.containsPoint(((Person) entity).getPosition())) {
                        i++;
                    }
                }
            }
            room.setNumOccupants(i);
        }

        entityGroup.update();
    }

    public Building getBuilding() {
        return building;
    }

    /**
     * this is invoked to give the world references to the shared manager classes
     * @param game the game to assign to this world
     */
    public void setGame(Game game) {
        this.game = game;
        this.generationHelper = new GenerationHelper(game); // we need to create a new generator because it references the game context
        this.tileManager = game.getTileManager();
        this.tileSize = tileManager.getTileSize();
    }

    public Entity[] getEntities() {
        return entityGroup.getEntities();
    }

    public GenerationHelper getGenerationHelper() {
        return generationHelper;
    }
}
