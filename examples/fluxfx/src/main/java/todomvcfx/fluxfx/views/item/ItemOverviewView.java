package todomvcfx.fluxfx.views.item;

import eu.lestard.fluxfx.View;
import eu.lestard.fluxfx.utils.ViewCellFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import todomvcfx.fluxfx.stores.ItemsStore;
import todomvcfx.fluxfx.stores.TodoItem;

public class ItemOverviewView implements View {

    @FXML
    public ListView<TodoItem> items;

    private final ItemsStore itemStore;

    public ItemOverviewView(ItemsStore itemStore) {
        this.itemStore = itemStore;
    }

    public void initialize() {

        ViewCellFactory<TodoItem, ItemView> cellFactory = new ViewCellFactory<>(ItemView.class, (todoItem, itemView) -> itemView.update(todoItem));

        items.setCellFactory(cellFactory);
        items.setItems(itemStore.getItems());
    }

}
