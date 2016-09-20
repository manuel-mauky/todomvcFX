package todomvcfx.fluxfx.views.item;

import eu.lestard.fluxfx.View;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.reactfx.EventSource;
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

    // internal state
    private EventSource<Boolean> editMode = new EventSource<>();


    public void initialize() {
        deleteButton.visibleProperty().bind(root.hoverProperty());

        completed.setOnAction(event -> publishCompletedAction());

        contentLabel.setOnMouseClicked(event -> {
            if(event.getClickCount() > 1) {
                editMode.push(true);
            }
        });

        // when the textfield loses the focus we leave edit mode
        contentInput.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                editMode.push(false);
            }
        });

        // when the user presses ENTER while the focus is at the text field
        // we leave edit mode.
        contentInput.setOnAction(event -> {
            editMode.push(false);
        });


        editMode.subscribe(this::changeEditMode);
        editMode.filter(Boolean.FALSE::equals) // only when we are leaving edit mode ...
                .subscribe(b -> publishEditAction()); // we are publish an edit action.
    }

    private void publishCompletedAction() {
        publishAction(new ChangeCompletedForSingleItemAction(id, completed.isSelected()));
    }

    private void publishEditAction() {
        publishAction(new EditAction(id, contentInput.getText()));
    }

    private void publishDeleteAction() {
        publishAction(new DeleteItemAction(id));
    }

    @FXML
    public void delete() {
        publishDeleteAction();
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

    private void changeEditMode(boolean editMode){
        contentInput.setVisible(editMode);
        if(editMode) {
            contentInput.requestFocus();
        }
        contentBox.setVisible(!editMode);
        completed.setVisible(!editMode);
    }
}
