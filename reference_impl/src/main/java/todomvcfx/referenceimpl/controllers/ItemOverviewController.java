package todomvcfx.referenceimpl.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import todomvcfx.referenceimpl.Repository;
import todomvcfx.referenceimpl.TodoItem;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ItemOverviewController {
    @FXML
    public ListView<TodoItem> items;

    private Map<TodoItem, Node> itemNodeCache = new HashMap<>();

    private final URL itemFxmlUrl;

    private final Repository repository;

    public ItemOverviewController(Repository repository) {
        this.repository = repository;
        final String pathToItemFxml = "/item/Item.fxml";

        itemFxmlUrl = this.getClass().getResource(pathToItemFxml);

        if(itemFxmlUrl == null) {
            throw new IllegalStateException("Can't find Item.fxml file with path: " + pathToItemFxml);
        }
    }

    public void initialize() {
        items.setItems(repository.itemsProperty());


        items.setCellFactory(new Callback<ListView<TodoItem>, ListCell<TodoItem>>() {
            @Override
            public ListCell<TodoItem> call(ListView<TodoItem> param) {
                return new ListCell<TodoItem>(){
                    @Override
                    protected void updateItem(TodoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(null);

                            if (!itemNodeCache.containsKey(item)) {
                                final Parent parent = loadItemFxml(item);
                                itemNodeCache.put(item, parent);
                            }

                            final Node node = itemNodeCache.get(item);

                            Node currentNode = getGraphic();
                            if (currentNode == null || !currentNode.equals(node)) {
                                setGraphic(node);
                            }
                        }
                    }
                };
            }
        });

    }

    private Parent loadItemFxml(TodoItem item) {
        FXMLLoader fxmlLoader = new FXMLLoader(itemFxmlUrl);

        try {

            ItemController itemController = new ItemController(item, repository);

            fxmlLoader.setController(itemController);
            fxmlLoader.load();

            return fxmlLoader.getRoot();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
