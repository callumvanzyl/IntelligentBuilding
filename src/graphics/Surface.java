package graphics;

import entity.DrawableEntity;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import math.Vector2;

/**
 * An object that is derivative of the JavaFX canvas,
 * it can draw an entity and be contained within a viewport
 */
class Surface extends Canvas {
    private GraphicsContext gc = getGraphicsContext2D();

    Surface() {
        setSize(new Vector2(0, 0)); // default size
    }

    /**
     * clear the surface with transparency
     */
    void clear() {
        gc.clearRect(0, 0, Double.MAX_VALUE, Double.MAX_VALUE); // we use max value to account for all situations
    }

    /**
     * draws the given entity at the relevant size and position
     * if the entity position is outside the bounds of the surface, it will not be drawn
     * @param entity the entity to draw
     */
    void drawEntity(DrawableEntity entity) {
        Vector2 position = entity.getPosition();
        Vector2 size = entity.getSize();

        gc.drawImage( // draw an entity using the graphics context
                entity.getSprite(),
                position.getX(),
                position.getY(),
                size.getX(),
                size.getY()
        );
    }

    /**
     * set the size of the canvas
     * @param size the new size of the canvas
     */
    void setSize(Vector2 size) {
        setHeight(size.getY());
        setWidth(size.getX());
    }
}
