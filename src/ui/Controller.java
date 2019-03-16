package ui;

import game.Game;
import javafx.stage.Stage;

public abstract class Controller {
    public Game game;
    public Stage stage;

    public void setGame(Game game) {
            this.game = game;
    }
    public void setStage(Stage stage) {
            this.stage = stage;
    }
}
