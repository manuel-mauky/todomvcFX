package todomvcfx.referenceimpl.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import todomvcfx.referenceimpl.Repository;
import todomvcfx.referenceimpl.TodoItem;

public class ControlsController {
    @FXML
    public Label itemsLeftLabel;

    public void initialize() {
        ListProperty<TodoItem> openItemsProperty = new SimpleListProperty<>(Repository.getSingleton().openItemsProperty());

        itemsLeftLabel.textProperty().bind(
                Bindings.concat(openItemsProperty.sizeProperty().asString(), " items left"));
    }

    public void all() {
        Repository.getSingleton().showAllItems();
    }

    public void active() {
        Repository.getSingleton().showOpenItems();
    }

    public void completed() {
        Repository.getSingleton().showCompletedItems();
    }
}

