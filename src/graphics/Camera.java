package graphics;

import javafx.scene.layout.Pane;
import math.Vector2;

/**
 * A utility class that is contained within the viewport
 * facilitates the navigation of the viewport via the mouse
 * must be attached to an active viewport to work as expected,
 */
class Camera {
    private static final int DAMPING = 150; // how much the camera is dampened when it is panned
    private static final double MAX_ZOOM = 5; // how far the user can zoom in
    private static final double MIN_ZOOM = 0.25; // how far they can zoom out

    private Vector2 currentOffset = new Vector2(); // the current offset is how far the camera has been panned from origin
    private double currentZoom = 1; // store current zoom, modifiable by mouse wheel
    private Vector2 previousMousePosition = new Vector2(); // for calculating delta of camera pan
    private Vector2 targetOffset = new Vector2(); // so that we can dampen movement towards camera

    private Pane innerInterest; // used for clipping the surface object properly
    private Pane outerInterest;

    /**
     * attaches the camera to a viewport
     * @param viewport the viewport to attach to
     */
    public void attachTo(Viewport viewport) {
        this.innerInterest = viewport.getMask(); // the inner clipping layer
        this.outerInterest = viewport; // outer clipping layer and container

        startListeners(); // begin listening for mouse input
    }

    /**
     * allow the camera to begin listening for mouse input
     * this only has to be called once! make sure innerinterest was set correctly.
     */
    private void startListeners() {
        // This happens when the user uses their mouse on the viewport
        innerInterest.setOnMousePressed(event -> {
            if (event.isSecondaryButtonDown()) { // if right click
                // pan the camera
                targetOffset.setX(outerInterest.getLayoutX()); // get new target position, used to calc delta
                previousMousePosition.setX(event.getX()); // set previous mouse position to current position

                targetOffset.setY(outerInterest.getLayoutY());
                previousMousePosition.setY(event.getY());
            }
        });

        // when the mouse is dragged
        innerInterest.setOnMouseDragged(event -> {
            if (event.isSecondaryButtonDown()) { // pan when they right-click drag
                double deltaX = event.getX() - previousMousePosition.getX(); // the difference between the start and current x position
                targetOffset.setX((targetOffset.getX() + deltaX)); // set target dampened pan position to new offset
                previousMousePosition.setX(event.getX()); // set previous mouse position

                double deltaY = event.getY() - previousMousePosition.getY();
                targetOffset.setY((targetOffset.getY() + deltaY));
                previousMousePosition.setY(event.getY());
            }
        });

        // when the mouse wheel is scrolled
        innerInterest.setOnScroll(event -> {
            currentZoom += (event.getDeltaY() / 1000); // div by 1000 to prevent massive zooming
            currentZoom = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, currentZoom)); // clamp it so it doesnt exceed the min or max value
        });
    }

    /**
     * update the camera and its damping
     */
    void update() {
        double i = Math.exp((double) (-DAMPING) / 60); // so it approaches the target slowly, depending on damping value
        currentOffset = currentOffset.lerp(targetOffset, i); // linear interpolation of current to target by factor of i
    }

    Vector2 getOffset() {
        return currentOffset;
    }

    double getZoom() {
        return currentZoom;
    }
}

