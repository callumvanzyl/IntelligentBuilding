package tool;

import javafx.scene.input.KeyCode;
import math.Vector2;
import world.World;

public abstract class Tool {
    public abstract void onStart(World world);
    public  abstract void onEnd(World world);

    public abstract void onKeyPressed(World world, KeyCode keycode);

    public abstract void onLeftMousePressed(World world, Vector2 point);
    public abstract void onMouseDragged(World world, Vector2 point);
    public abstract void onMouseMoved(World world, Vector2 point);
    public abstract void onLeftMouseReleased(World world, Vector2 point);
}
