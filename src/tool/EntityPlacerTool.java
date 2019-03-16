package tool;

import entity.DrawableEntity;
import javafx.scene.input.KeyCode;
import math.Vector2;
import tile.Tile;
import world.Building;
import world.World;

public class EntityPlacerTool extends Tool {
    private DrawableEntity activeEntity;
    private boolean isActive = true;
    private boolean isValid;
    private Tile placer;

    @Override
    public void onStart(World world) {
    }

    @Override
    public void onEnd(World world) {
        isActive = false;
        if (placer != null)
            placer.destroy();
    }

    @Override
    public void onKeyPressed(World world, KeyCode keycode) {
        if (keycode == KeyCode.ESCAPE) {
            onEnd(world);
        }
    }

    @Override
    public void onLeftMousePressed(World world, Vector2 point) {
        if (isActive && isValid) {
            Vector2 gp = world.worldToGridPoint(point);
            activeEntity.setDrawingPriority(999);
            activeEntity.setPosition(gp.getX(), gp.getY());
            activeEntity.setSize(32, 32);
            activeEntity.setRoom(world.getRoomAtPoint(point));
            world.addEntity(activeEntity);
            onEnd(world);
        }
    }

    @Override
    public void onMouseDragged(World world, Vector2 point) {

    }

    @Override
    public void onMouseMoved(World world, Vector2 point) {
        Building building = world.getBuilding();
        isValid = (building != null && building.getPolygon().containsPoint(point));
        if (isActive) {
            if (placer != null)
                placer.destroy();
            byte tileId = isValid ? (byte) 126 : (byte) 127;
            placer = world.getGenerationHelper().placeTile(world.worldToGridPoint(point), tileId, 999);
        }
    }

    @Override
    public void onLeftMouseReleased(World world, Vector2 point) {

    }

    public void setEntity(DrawableEntity entity) {
        this.activeEntity = entity;
    }
}
