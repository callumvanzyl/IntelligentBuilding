package tool;

import game.Game;
import javafx.scene.input.KeyCode;
import math.Vector2;

public class ToolManager {
    private Game game;
    private Tool tool;

    public ToolManager(Game game) {
        this.game = game;
    }

    public void emitStart() {
        if (tool != null && game.getWorld() != null)
            tool.onStart(game.getWorld());
    }

    public void emitEnd() {
        if (tool != null && game.getWorld() != null)
            tool.onEnd(game.getWorld());
    }

    public void emitKeyPressedEvent(KeyCode keycode) {
        if (tool != null && game.getWorld() != null)
            tool.onKeyPressed(game.getWorld(), keycode);
    }

    public void emitLeftMousePressedEvent(Vector2 point) {
        if (tool != null && game.getWorld() != null)
                tool.onLeftMousePressed(game.getWorld(), point);
    }

    public void emitMouseDraggedEvent(Vector2 point) {
        if (tool != null && game.getWorld() != null)
            tool.onMouseDragged(game.getWorld(), point);
    }

    public void emitMouseMovedEvent(Vector2 point) {
        if (tool != null && game.getWorld() != null)
            tool.onMouseMoved(game.getWorld(), point);
    }

    public void emitLeftMouseReleasedEvent(Vector2 point) {
        if (tool != null && game.getWorld() != null)
            tool.onLeftMouseReleased(game.getWorld(), point);
    }

    public void setTool(Tool tool) {
        if (tool != null)
            emitEnd();
        this.tool = tool;
        emitStart();
    }
}
