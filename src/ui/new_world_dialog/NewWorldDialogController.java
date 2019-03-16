package ui.new_world_dialog;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import math.Vector2;
import ui.Controller;

public class NewWorldDialogController extends Controller {
    @FXML private TextField height;
    @FXML private TextField width;

    @FXML
    private void onCreateButtonPressed() {
        try {
            int worldHeight = Integer.parseInt(height.getText());
            int worldWidth = Integer.parseInt(width.getText());
            if (worldHeight >= 8 && worldHeight <= 64 && worldWidth >= 8 && worldWidth <= 64) {
                game.resetWorld();
                game.getViewport().resetSurface();
                game.getWorld().createGrassyPlain(new Vector2(worldWidth, worldHeight));
                stage.close();
            }
        } catch(Exception e) { }
    }
}
