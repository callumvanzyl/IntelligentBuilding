package tool;

import entity.EntityGroup;
import javafx.scene.input.KeyCode;
import math.Vector2;
import tile.Tile;
import world.Building;
import world.World;

import java.util.ArrayList;

public class RoomCreatorTool extends Tool {
    private ArrayList<Vector2> allVertices = new ArrayList();
    private EntityGroup allWalls = new EntityGroup();
    private EntityGroup currentWall = new EntityGroup();
    private Tile currentDoor;

    private boolean isActive = false;
    private boolean isValid = false;
    private boolean isPlacingDoor = false;
    private boolean placedDoor = false;

    private Vector2 previousEndPoint;
    private Vector2 startPoint;
    private Vector2 endPoint;
    private Vector2 doorPoint;

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
                if (isPlacingDoor && placedDoor) {
                    currentDoor.destroy();
                    world.createRoom(allVertices.toArray(new Vector2[0]), doorPoint);
                    onEnd(world);
                }
                else {
                     isPlacingDoor = true;
                }
            }
        }
        if (keycode == KeyCode.ESCAPE) {
            onEnd(world);
        }
    }

    @Override
    public void onLeftMousePressed(World world, Vector2 point) {
        if (isValid) {
            if (isPlacingDoor) {
                placedDoor = true;
                doorPoint = world.worldToGridPoint(point);
                isActive = false;
            } else {
                allVertices.add(world.worldToGridPoint(point));
                if (isActive) {
                    allWalls.combine(world.getGenerationHelper().drawWallWithTile(startPoint, endPoint, (byte) 126, 99));
                    currentWall.destroy();
                } else {
                    isActive = true;
                }
                startPoint = world.worldToGridIndex(point);
            }
        }
    }

    @Override
    public void onMouseDragged(World world, Vector2 point) {

    }

    @Override
    public void onMouseMoved(World world, Vector2 point) {
        if (!isPlacingDoor) {
            Building building = world.getBuilding();
            isValid = (building != null && building.getPolygon().containsPoint(point));
            if (isActive) {
                endPoint = world.worldToGridIndex(point);
                if (endPoint != previousEndPoint) {
                    byte tileId = isValid ? (byte) 126 : (byte) 127;
                    currentWall.destroy();
                    currentWall = world.getGenerationHelper().drawWallWithTile(startPoint, endPoint, tileId, 99);
                    previousEndPoint = endPoint;
                }
            }
        } else {
            if (isActive) {
                if (currentDoor != null)
                    currentDoor.destroy();
                currentDoor = world.getGenerationHelper().placeTile(world.worldToGridPoint(point), (byte) 127, 99);
                previousEndPoint = endPoint;
            }
        }
    }

    @Override
    public void onLeftMouseReleased(World world, Vector2 point) {

    }
}
