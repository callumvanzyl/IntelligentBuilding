package game;

import entity.EntityManager;
import graphics.Viewport;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tile.TileManager;
import tool.ToolManager;
import ui.editor.EditorController;
import world.World;

/**
 * The core game object, also contains the main method.
 * Contains shared managers and facilitates the exchanging of data between elements of the MVC model.
 * Many classes require this context.
 */
public class Game extends Application {
    private EntityManager entityManager = new EntityManager(this); // A shared entity manager
    private TileManager tileManager = new TileManager(this); // A shared tile manager
    private ToolManager toolManager = new ToolManager(this); // A shared tool manager
    private Viewport viewport = new Viewport(this); // A viewport

    private World world; // Initialise the world as empty

    private boolean paused = false;

    public static void main(String[] args) {
        launch();
    }

    /***
     * Destroys and reinitialises the world.
     * Also gracefully ends the current tool process.
     */
    public void resetWorld() {
        world = new World(this);
        toolManager.emitEnd(); // Ends the current tool process, if any
    }

    private void run() {
        new AnimationTimer() {
            long previous = System.nanoTime(); // Used to calculate delta time between current and previous frame

            public void handle(long now) { // Called every tick
                if (world != null) {
                    long delta = (now - previous);
                    previous = now;

                    update(delta);
                    draw();
                }
            }
        }.start();
    }

    public void togglePause() {
        paused = !paused;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/editor/editor.fxml")); // Load editor FXML file
        Parent root = loader.load();
        EditorController controller = loader.getController(); // Get the controller class associated with the editor (EditorController)
        controller.setStage(primaryStage);
        controller.setGame(this);
        primaryStage.setTitle("Intelligent Building");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();

        viewport.attachTo(EditorController.getViewport()); // Get a reference to the viewport pane object and attach the actual viewport object to it

        run();
    }

    /**
     * Invokes the update method of the world and viewport.
     * This would update all thinking entities and modify the internal values of the camera, etc.
     * @param delta The delta time between current and previous frame
     */
    private void update(double delta) {
        viewport.update();
        if (!paused)
            world.update();
        else
            toolManager.emitEnd();
    }

    /**
     * Invokes the draw method of the viewport
     * This would redraw all drawable entities
     */
    private void draw() {
        viewport.draw(world);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public boolean isPaused() {
        return paused;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public ToolManager getToolManager() {
        return toolManager;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
        world.setGame(this); // Add a reference to the core game so that it can access shared managers
    }
}