package ui.add_entity_dialog;

import entity.DrawableEntity;
import entity.Entity;
import entity.EntityManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import tool.EntityPlacerTool;
import ui.Controller;

import java.util.HashMap;

public class AddEntityDialogController extends Controller {
    @FXML Label entityDescription;
    @FXML ImageView entitySprite;
    @FXML AnchorPane innerScrollPane;

    private String selectedEntityClassName;

    public void loadEntities() {
        EntityManager entityManager = game.getEntityManager();

        HashMap<String, String> friendlyNameToClass = new HashMap<>();
        for (String className: entityManager.getEnabledEntities()) {
            Entity temp = game.getEntityManager().getEntity(className);
            friendlyNameToClass.put(temp.getName(), className);
        }

        ObservableList<String> entityNamesList = FXCollections.observableArrayList(friendlyNameToClass.keySet());
        ListView<String> entities = new ListView<>(entityNamesList);
        entities.setOrientation(Orientation.VERTICAL);
        entities.setPrefSize(185, 512);
        innerScrollPane.getChildren().add(entities);

        entities.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedEntityClassName = friendlyNameToClass.get(newValue);
            Entity temp = game.getEntityManager().getEntity(friendlyNameToClass.get(newValue));
            entityDescription.setText(temp.getDescription());
            if (temp instanceof DrawableEntity)
                entitySprite.setImage(((DrawableEntity) temp).getSprite());
        });
    }

    public void onAddToWorldButtonPressed() {
        if (selectedEntityClassName != null) {
            EntityPlacerTool ept = new EntityPlacerTool();
            ept.setEntity((DrawableEntity) game.getEntityManager().getEntity(selectedEntityClassName));
            game.getToolManager().setTool(ept);
            stage.close();
        }
    }
}
