package world;

import entity.EntityGroup;
import math.Polygon;
import math.Vector2;

import java.io.Serializable;

public class Building implements Serializable {
    EntityGroup building = new EntityGroup();

    Polygon polygon;
    World world;

    Building(Vector2[] vertices, World world) {
        Polygon polygon = new Polygon();
        polygon.setVertices(vertices);
        this.polygon = polygon;
        this.world = world;
        building.combine(world.getGenerationHelper().generateBuilding(vertices));
    }

    public void destroy() {
        building.destroy();
    }

    public Polygon getPolygon() {
        return polygon;
    }
}
