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

    private final Repository repository;

    public ControlsController(Repository repository) {
        this.repository = repository;
    }

    public void initialize() {
        ListProperty<TodoItem> openItemsProperty = new SimpleListProperty<>(repository.openItemsProperty());

        itemsLeftLabel.textProperty().bind(
                Bindings.concat(openItemsProperty.sizeProperty().asString(), " items left"));
    }

    public void all() {
        repository.showAllItems();
    }

    public void active() {
        repository.showOpenItems();
    }

    public void completed() {
        repository.showCompletedItems();
    }
}

