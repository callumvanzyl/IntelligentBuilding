package world;

import entity.DrawableEntity;
import entity.Entity;
import entity.EntityGroup;
import game.Game;
import tile.Tile;
import math.LineGenerator;
import math.Polygon;
import math.Vector2;
import tile.TileManager;

import java.util.ArrayList;

public class GenerationHelper {
    private Game game;
    private TileManager tileManager;
    private int tileSize;

    private LineGenerator lineGenerator = new LineGenerator();

    public GenerationHelper(Game game) {
        this.tileManager = game.getTileManager();
        this.tileSize = tileManager.getTileSize();
        this.game = game;
    }

    EntityGroup fillPolygonWithTile(Polygon polygon, byte tileId, int drawingPriority) {
        EntityGroup group = new EntityGroup();
        World world = game.getWorld();

        Vector2 lep = polygon.getLeastExtremePoint();
        Vector2 mep = polygon.getMostExtremePoint();
        for (double x = lep.getX(); x<mep.getX(); x+=tileSize) {
            for (double y = lep.getY(); y<mep.getY(); y+=tileSize) {
                if (polygon.containsPoint(new Vector2(x, y))) {
                    Tile tile = tileManager.getTileById(tileId);
                    tile.setDrawingPriority(drawingPriority);
                    tile.setPosition(x, y);
                    tile.setSize(tileSize, tileSize);
                    group.add(tile);
                    world.addEntity(tile);
                }
            }
        }

        return group;
    }

    public EntityGroup drawWallWithTile(Vector2 p1, Vector2 p2, byte tileId, int drawingPriority) {
        EntityGroup group = new EntityGroup();
        World world = game.getWorld();

        ArrayList<Vector2> line = lineGenerator.generate(p1, p2);
        for(Vector2 point: line) {
            Tile tile = tileManager.getTileById(tileId);
            tile.setDrawingPriority(drawingPriority);
            tile.setPosition(tileSize*point.getX(), tileSize*point.getY());
            tile.setSize(tileSize, tileSize);
            group.add(tile);
            world.addEntity(tile);
        }

        return group;
    }

    public EntityGroup generateBuilding(Vector2[] vertices) {
        EntityGroup group = new EntityGroup();
        World world = game.getWorld();

        for(int i = 0; i<vertices.length-1; i++)
            group.combine(drawWallWithTile(world.worldToGridIndex(vertices[i]), world.worldToGridIndex(vertices[i+1]), (byte) 1, 96));

        Polygon floor = new Polygon();
        floor.setVertices(vertices);
        group.combine(fillPolygonWithTile(floor, (byte) 2, 95));

        return group;
    }

    public EntityGroup generateRoom(Vector2[] vertices, Vector2 doorPoint) {
        EntityGroup group = new EntityGroup();
        World world = game.getWorld();

        for(int i = 0; i<vertices.length-1; i++)
            group.combine(drawWallWithTile(world.worldToGridIndex(vertices[i]), world.worldToGridIndex(vertices[i+1]), (byte) 3, 98));

        for (Entity entity: group.getEntities()) {
            if (entity instanceof DrawableEntity) {
                if (((DrawableEntity) entity).getPosition().equals(doorPoint))
                    entity.isCollidable = false;
            }
        }
        group.add(placeTile(doorPoint, (byte) 4, 99));

        Polygon floor = new Polygon();
        floor.setVertices(vertices);
        group.combine(fillPolygonWithTile(floor, (byte) 4, 97));

        return group;
    }

    public Tile placeTile(Vector2 point, byte tileId, int drawingPriority) {
        EntityGroup group = new EntityGroup();
        World world = game.getWorld();

        Tile tile = tileManager.getTileById(tileId);
        tile.setDrawingPriority(drawingPriority);
        tile.setPosition(point.getX(), point.getY());
        tile.setSize(tileSize, tileSize);
        group.add(tile);
        world.addEntity(tile);

        return tile;
    }
}
