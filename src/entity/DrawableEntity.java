package entity;

import io.ImageCache;
import javafx.scene.image.Image;
import math.Vector2;
import world.Room;
import world.World;

/**
 * A drawable entity.
 * It contains a shared image cache, and contains methods relating to its appearance in the world.
 */
public class DrawableEntity extends Entity {
    protected static ImageCache spriteCache = new ImageCache(); // A shared sprite cache

    protected Room room;
    private Vector2 position = new Vector2(); // If these aren't set, the entity won't be visible!
    private Vector2 size = new Vector2();
    protected String spritePath = "/res/sprites/Placeholder.bmp"; // To save memory, the actual image isn't stored by the class

    private int drawingPriority = 0; // Entities with a higher drawing priority are drawn on top

    public DrawableEntity(World world) {
        super(world);
    }

    public int getDrawingPriority() {
        return drawingPriority;
    }

    public void setDrawingPriority(int drawingPriority) {
        this.drawingPriority = drawingPriority;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(double x, double y) {
        position.setX(x);
        position.setY(y);
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(double w, double h) {
        size.setX(w);
        size.setY(h);
    }

    /**
     * Fetches the sprite at spritePath from the image cache.
     * @return The image located at spritePath
     */
    public Image getSprite() {
        return spriteCache.getImage(spritePath);
    }
}
