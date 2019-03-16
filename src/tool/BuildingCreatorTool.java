package tool;

import entity.EntityGroup;
import javafx.scene.input.KeyCode;
import math.Vector2;
import world.World;

import java.util.ArrayList;

public class BuildingCreatorTool extends Tool {
    private ArrayList<Vector2> allVertices = new ArrayList();
    private EntityGroup allWalls = new EntityGroup();
    private EntityGroup currentWall = new EntityGroup();

    private boolean isActive = false;

    private Vector2 previousEndPoint;
    private Vector2 startPoint;
    private Vector2 endPoint;

    @Override
    public void onStart(World world) {
        allVertices = new ArrayList();
        allWalls.destroy();
        currentWall.destroy();
        isActive = false;
    }

    @Override
    public void onEnd(World world) {
        allVertices = new ArrayList();
        allWalls.destroy();
        currentWall.destroy();
        isActive = false;
    }

    @Override
    public void onKeyPressed(World world, KeyCode keycode) {
        if (keycode == KeyCode.ENTER) {
            if(allVertices.size() > 2 && allVertices.get(0).equals(allVertices.get(allVertices.size()-1))) {
                world.createBuilding(allVertices.toArray(new Vector2[0]));
                onEnd(world);
            }
        }
        if (keycode == KeyCode.ESCAPE) {
            onEnd(world);
        }
    }

    @Override
    public void onLeftMousePressed(World world, Vector2 point) {
        allVertices.add(world.worldToGridPoint(point));
        if (isActive) {
            allWalls.combine(world.getGenerationHelper().drawWallWithTile(startPoint, endPoint, (byte) 126, 99));
            currentWall.destroy();
        } else {
            isActive = true;
        }
        startPoint = world.worldToGridIndex(point);
    }

    @Override
    public void onMouseDragged(World world, Vector2 point) {

    }

    @Override
    public void onMouseMoved(World world, Vector2 point) {
        if (isActive) {
            endPoint = world.worldToGridIndex(point);
            if (endPoint != previousEndPoint) {
                currentWall.destroy();
                currentWall = world.getGenerationHelper().drawWallWithTile(startPoint, endPoint, (byte) 126, 99);
                previousEndPoint = endPoint;
            }
        }
    }

    @Override
    public void onLeftMouseReleased(World world, Vector2 point) {

    }
}
