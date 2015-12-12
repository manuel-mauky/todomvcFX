package todomvcfx.referenceimpl;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javax.inject.Singleton;

@Singleton
public class Repository {

    private ObservableList<TodoItem> allItems = FXCollections.observableArrayList(item -> new Observable[]{item.doneProperty()});
    private FilteredList<TodoItem> completedItems = new FilteredList<TodoItem>(allItems, TodoItem::isDone);
    private FilteredList<TodoItem> openItems = new FilteredList<TodoItem>(allItems, (item) -> !item.isDone());


    private ListProperty<TodoItem> itemsProperty = new SimpleListProperty<>(allItems);


    public ObservableList<TodoItem> itemsProperty() {
        return itemsProperty;
    }

    public ObservableList<TodoItem> allItemsProperty() {
        return allItems;
    }

    public ObservableList<TodoItem> completedItemsProperty() {
        return completedItems;
    }

    public ObservableList<TodoItem> openItemsProperty() {
        return openItems;
    }


    public void showAllItems() {
        itemsProperty.set(allItems);
    }

    public void showCompletedItems() {
        itemsProperty.set(completedItems);
    }

    public void showOpenItems() {
        itemsProperty.set(openItems);
    }

    public void addItem(TodoItem item) {
        allItems.add(item);
    }

    public void deleteItem(TodoItem item) {
        allItems.remove(item);
    }

}