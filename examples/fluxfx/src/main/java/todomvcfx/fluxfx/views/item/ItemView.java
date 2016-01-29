package todomvcfx.fluxfx.views.item;

import eu.lestard.fluxfx.View;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import todomvcfx.fluxfx.actions.ChangeCompletedForSingleItemAction;
import todomvcfx.fluxfx.actions.DeleteItemAction;
import todomvcfx.fluxfx.actions.EditAction;
import todomvcfx.fluxfx.stores.TodoItem;

public class ItemView implements View {

    public static final String STRIKETHROUGH_CSS_CLASS = "strikethrough";

    @FXML
    public Label contentLabel;

    @FXML
    public CheckBox completed;

    @FXML
    public TextField contentInput;

    @FXML
    public HBox root;
    @FXML
    public Button deleteButton;

    @FXML
    public HBox contentBox;

    private String id;

    // state
    private BooleanProperty editMode = new SimpleBooleanProperty();

    public void initialize() {
        deleteButton.setVisible(false);
        root.setOnMouseEntered(event -> deleteButton.setVisible(true));
        root.setOnMouseExited(event -> deleteButton.setVisible(false));


        completed.setOnAction(event -> publishAction(new ChangeCompletedForSingleItemAction(id, completed.isSelected())));

        contentLabel.setOnMouseClicked(event -> {
            if(event.getClickCount() > 1) {
                editMode.setValue(true);
            }
        });

        contentInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                editMode.setValue(false);
            }
        });

        contentInput.setOnAction(event -> {
            editMode.setValue(false);
        });

        editMode.addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                publishAction(new EditAction(id, contentInput.getText()));
            }
            initEditMode(newValue);
        });
    }

    public void update(TodoItem item) {
        id = item.getId();
        contentLabel.setText(item.getText());
        contentInput.setText(item.getText());
        completed.setSelected(item.isCompleted());
        if (item.isCompleted()) {
            contentLabel.getStyleClass().add(STRIKETHROUGH_CSS_CLASS);
        } else {
            contentLabel.getStyleClass().removeAll(STRIKETHROUGH_CSS_CLASS);
        }

    }

    private void initEditMode(boolean editMode){
        contentInput.setVisible(editMode);
        if(editMode) {
            contentInput.requestFocus();
        }
        contentBox.setVisible(!editMode);
        completed.setVisible(!editMode);
    }

    public void delete() {
        publishAction(new DeleteItemAction(id));
    }
}
