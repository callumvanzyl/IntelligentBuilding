package ui.editor;

import io.WorldSerializer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tool.BuildingCreatorTool;
import tool.RoomCreatorTool;
import ui.Controller;
import ui.add_entity_dialog.AddEntityDialogController;
import ui.new_world_dialog.NewWorldDialogController;

import java.io.File;

public class EditorController extends Controller {
    private static final FileChooser.ExtensionFilter FILTER = new FileChooser.ExtensionFilter("Intelligent Building", "*.ib");

    @FXML private Menu fileMenu;
    @FXML private MenuItem saveAsMenuItem;
    @FXML private Pane viewport;

    @FXML private MenuItem addBuildingMenuItem;
    @FXML private MenuItem addRoomMenuItem;
    @FXML private MenuItem addEntityMenuItem;

    private static Pane viewportReference;

    public void initialize() {
        fileMenu.setOnShown(event -> {
            if (game.getWorld() != null)
                saveAsMenuItem.setDisable(false);
            else
                saveAsMenuItem.setDisable(true);
        });
        viewportReference = viewport;
    }

    @FXML
    private void onAddBuildingMenuItemPressed() {
        game.getToolManager().setTool(new BuildingCreatorTool());
    }

    @FXML
    private void onAddRoomMenuItemPressed() {
        game.getToolManager().setTool(new RoomCreatorTool());
    }

    @FXML
    private void onAddEntityMenuItemPressed() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/add_entity_dialog/add_entity_dialog.fxml"));
        Parent root = loader.load();
        AddEntityDialogController controller = loader.getController();
        Stage dialog = new Stage();
        controller.setGame(game);
        controller.setStage(dialog);
        dialog.initOwner(stage);
        dialog.setResizable(false);
        dialog.setScene(new Scene(root, 400, 250));
        dialog.show();
        controller.loadEntities();
    }

    @FXML
    private void onAddThingButtonPressed() {
        if (game.getWorld() != null) {
            if (game.getWorld().getBuilding() == null) {
                addBuildingMenuItem.setDisable(false);
                addEntityMenuItem.setDisable(true);
                addRoomMenuItem.setDisable(true);
            } else {
                addBuildingMenuItem.setDisable(true);
                addEntityMenuItem.setDisable(false);
                addRoomMenuItem.setDisable(false);
            }
        }

        if(game.isPaused()) {
            addBuildingMenuItem.setDisable(true);
            addEntityMenuItem.setDisable(true);
            addRoomMenuItem.setDisable(true);
        }
    }

    @FXML
    private void onTogglePauseButtonPressed() {
        game.togglePause();
    }

    @FXML
    private void onNewWorldMenuItemPressed() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/new_world_dialog/new_world_dialog.fxml"));
        Parent root = loader.load();
        NewWorldDialogController controller = loader.getController();
        Stage dialog = new Stage();
        controller.setGame(game);
        controller.setStage(dialog);
        dialog.initOwner(stage);
        dialog.setResizable(false);
        dialog.setScene(new Scene(root, 250, 100));
        dialog.show();
    }

    @FXML
    private void onOpenMenuItemPressed() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(FILTER);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setInitialFileName("UntitledBuilding");
        fileChooser.setTitle("Open Building...");
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            WorldSerializer serializer = new WorldSerializer();
            game.getViewport().resetSurface();
            game.setWorld(serializer.load(file.getPath()));
        }
    }

    @FXML
    private void onSaveAsMenuItemPressed() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(FILTER);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setTitle("Save Building As...");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            WorldSerializer serializer = new WorldSerializer();
            serializer.save(file.getPath(), game.getWorld());
        }
    }

    public static Pane getViewport() {
        return viewportReference;
    }
}

