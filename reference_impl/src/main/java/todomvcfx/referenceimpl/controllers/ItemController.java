package todomvcfx.referenceimpl.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import todomvcfx.referenceimpl.Repository;
import todomvcfx.referenceimpl.TodoItem;

public class ItemController {
    @FXML
    public CheckBox completed;
    @FXML
    public HBox contentBox;
    @FXML
    public Label contentLabel;
    @FXML
    public Button deleteButton;
    @FXML
    public TextField contentInput;
    @FXML
    public HBox root;

    private TodoItem todoItem;

    public ItemController(TodoItem item) {
        this.todoItem = item;
    }

    public void initialize() {
        completed.selectedProperty().bindBidirectional(todoItem.doneProperty());

        contentLabel.textProperty().bindBidirectional(todoItem.textProperty());
        contentInput.textProperty().bindBidirectional(todoItem.textProperty());

        contentBox.setOnMouseClicked(event -> {
            if(event.getClickCount() > 1) {
                enableEditMode();
            }
        });

        contentInput.setOnAction(event -> disableEditMode());

        contentInput.focusedProperty().addListener((obs, oldV, newV) -> {
            if (!newV) {
                disableEditMode();
            }
        });

        root.setOnMouseEntered(event -> {
            deleteButton.setVisible(true);
        });

        root.setOnMouseExited(event -> {
            deleteButton.setVisible(false);
        });
    }

    private void disableEditMode() {
        contentInput.setVisible(false);
        contentLabel.setVisible(true);
    }

    private void enableEditMode() {
        contentLabel.setVisible(false);
        contentInput.setVisible(true);
        contentInput.requestFocus();
    }

    public void delete() {
        Repository.getSingleton().deleteItem(todoItem);
    }
}
