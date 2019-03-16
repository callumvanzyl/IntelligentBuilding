package graphics;

import entity.DrawableEntity;
import entity.Entity;
import game.Game;
import javafx.beans.value.ChangeListener;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import math.Vector2;
import tool.ToolManager;
import world.World;

/**
 * a viewport used to display the world
 * must include a surface to work
 */
public class Viewport extends Pane {
    private Camera camera; // a camera that pans around the world
    private Pane mask = new Pane(); // a mask that clips the layer
    private Surface surface = new Surface(); // a surface that is drawn on
    private ToolManager toolManager; // the tool manager

    public Viewport(Game game) {
        camera = new Camera(); // new camera
        camera.attachTo(this); // attach the camera to the viewport

        this.toolManager = game.getToolManager(); // get tool manager

        mask.getChildren().add(this); // add to javafx children

        resetSurface();

        startListeners(); // start listeners
    }

    /**
     * attach the viewport to a pane so that it is targeted in updates
     * @param pane the pane to attach to
     */
    public void attachTo(Pane pane) {
        pane.getChildren().add(mask);
        bindSize(pane, mask); // so they are always the same size

        Rectangle clip = new Rectangle(); // clipping rectangle
        bindSize(mask, clip);
        mask.setClip(clip);
    }

    /**
     * overloaded method that can bind the size of a pane to either another pane or a rectangle
     * @param subject the thing to change the size of
     * @param target the thing to get the size of from
     */
    private void bindSize(Pane subject, Pane target) {
        target.setPrefWidth(subject.getWidth());
        target.setPrefHeight(subject.getHeight());

        ChangeListener<Number> sizeListener = (observable, oldValue, newValue) -> { // when size changes
            target.setPrefWidth(subject.getWidth());
            target.setPrefHeight(subject.getHeight());
        };

        subject.widthProperty().addListener(sizeListener);
        subject.heightProperty().addListener(sizeListener);
    }

    private void bindSize(Pane subject, Rectangle target) {
        target.setWidth(subject.getWidth());
        target.setHeight(subject.getHeight());

        ChangeListener<Number> sizeListener = (observable, oldValue, newValue) -> {
            target.setWidth(subject.getWidth());
            target.setHeight(subject.getHeight());

        };

        subject.widthProperty().addListener(sizeListener);
        subject.heightProperty().addListener(sizeListener);
    }

    /**
     * clear surface
     */
    public void resetSurface() {
        if (surface != null) {
            surface.clear(); // i dont know why i have to do this, maybe javafx caching
            getChildren().remove(surface);
        }
        surface = new Surface();
        surface.setSize(new Vector2(1024, 1024));
        getChildren().add(surface);
    }

    /**
     * begin listening for mouse input
     */
    private void startListeners() {
        this.setOnKeyPressed(event -> toolManager.emitKeyPressedEvent(event.getCode()));

        // when the mouse is pressed
        this.setOnMousePressed(event -> {
            //this.requestFocus();
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                toolManager.emitLeftMousePressedEvent(new Vector2(event.getX(), event.getY())); // invoke tool manager to emit left press event
            }
        });

        this.setOnMouseDragged(event -> toolManager.emitMouseDraggedEvent(new Vector2(event.getX(), event.getY())));

        this.setOnMouseMoved(event -> {
            this.requestFocus();
            toolManager.emitMouseMovedEvent(new Vector2(event.getX(), event.getY()));
        });

        this.setOnMouseReleased(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                toolManager.emitLeftMouseReleasedEvent(new Vector2(event.getX(), event.getY()));
            }
        });
    }

    public void update() {
        camera.update();
    }

    public void draw(World world) {
        Vector2 offset = camera.getOffset();
        setLayoutX(offset.getX());
        setLayoutY(offset.getY());

        setScaleX(camera.getZoom());
        setScaleY(camera.getZoom());

        surface.clear();

        for(Entity entity: world.getEntities()) {
            if (entity instanceof DrawableEntity)
                surface.drawEntity((DrawableEntity) entity);
        }
    }

    Pane getMask() {
        return mask;
    }

    public Surface getSurface() {
        return surface;
    }
}
