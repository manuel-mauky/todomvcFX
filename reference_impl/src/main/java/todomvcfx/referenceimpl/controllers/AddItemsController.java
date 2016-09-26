package todomvcfx.referenceimpl.controllers;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import todomvcfx.referenceimpl.Repository;
import todomvcfx.referenceimpl.TodoItem;

public class AddItemsController {
    @FXML
    public CheckBox selectAll;
    @FXML
    public TextField addInput;

    private final Repository repository;

    public AddItemsController(Repository repository) {
        this.repository = repository;
    }


    public void initialize() {

//        addInput.setOnAction(event -> {
//            final String currentText = addInput.getText();
//
//            // check input
//            if(currentText == null || currentText.trim().isEmpty()) {
//                return;
//            }
//
//            // create and add item
//            TodoItem newItem = new TodoItem(currentText);
//            repository.addItem(newItem);
//
//            // reset input
//            addInput.setText("");
//        });



        selectAll.setOnAction(event -> {
            final boolean selected = selectAll.isSelected();
            repository.allItemsProperty().forEach(item -> {
                item.setDone(selected);
            });

            // while iterating through the items and setting them to done,
            // the selectAll-checkbox can switch it's state based on other constraints
            // Therefore at the end the checkbox has to be set to the value again.
            selectAll.setSelected(selected);
        });


        repository.allItemsProperty().addListener((ListChangeListener<TodoItem>) c -> {
            c.next();

            selectAll.setVisible(! repository.allItemsProperty().isEmpty());



            // if the checkbox is marked...
            if(selectAll.isSelected()) {

                // we check if there is any item that is not "done" now.
                // If this is the case, we uncheck the checkbox
                selectAll.setSelected(!repository.allItemsProperty().stream()
                        .anyMatch(item -> !item.isDone()));
            } else {

                // if the checkbox is not marked yet
                // we check if there all items are done now.
                // if this is the case, we mark the checkbox.
                selectAll.setSelected(repository.allItemsProperty().stream()
                        .allMatch(item -> item.isDone()));
            }
        });

    }

}
