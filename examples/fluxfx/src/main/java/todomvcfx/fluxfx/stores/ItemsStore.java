package todomvcfx.fluxfx.stores;

import eu.lestard.fluxfx.Store;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import org.reactfx.EventSource;
import org.reactfx.EventStream;
import todomvcfx.fluxfx.actions.AddItemAction;
import todomvcfx.fluxfx.actions.ChangeCompletedForAllItemsAction;
import todomvcfx.fluxfx.actions.ChangeCompletedForSingleItemAction;
import todomvcfx.fluxfx.actions.ChangeFilterAction;
import todomvcfx.fluxfx.actions.DeleteItemAction;
import todomvcfx.fluxfx.actions.EditAction;

import javax.inject.Singleton;

@Singleton
public class ItemsStore extends Store {

    private ObservableList<TodoItem> items = FXCollections.observableArrayList(
            item -> new Observable[]{item.textProperty(), item.completedProperty()});

    private FilteredList<TodoItem> filteredData = new FilteredList<TodoItem>(items, s -> true);
    private ChangeFilterAction.VisibilityType filterStatus = ChangeFilterAction.VisibilityType.ALL;

    private EventSource<String> inputText = new EventSource<>();
    private EventSource<Boolean> selectAllCheckbox = new EventSource<>();

    private ReadOnlyIntegerWrapper numberOfItemsLeft = new ReadOnlyIntegerWrapper();

    public ItemsStore() {
        subscribe(AddItemAction.class, this::processAddItemAction);
        subscribe(DeleteItemAction.class, this::processDeleteItemAction);
        subscribe(ChangeCompletedForSingleItemAction.class, this::processChangeCompletedSingleItemAction);
        subscribe(ChangeFilterAction.class, this::processChangeFilterAction);
        subscribe(ChangeCompletedForAllItemsAction.class, this::processChangeCompletedAllItemsAction);
        subscribe(EditAction.class, this::processEditAction);
    }

    void processEditAction(EditAction action){
        items.stream()
                .filter(item -> item.getId().equals(action.getItemId()))
                .findAny()
                .ifPresent(item -> item.setText(action.getNewText()));
    }


    void processChangeCompletedAllItemsAction(ChangeCompletedForAllItemsAction action) {
        selectAllCheckbox.push(action.isNewState());
        items.stream()
                .filter(item -> item.isCompleted() != action.isNewState())
                .forEach(item -> item.setCompleted(action.isNewState()));

        updateFilterPredicate();
        updateNumberOfItemsLeft();
    }

    void processDeleteItemAction(DeleteItemAction action) {
        items.stream()
                .filter(item -> item.getId().equals(action.getId()))
                .findAny()
                .ifPresent(items::remove);

        selectAllCheckbox.push(items.stream()
                .allMatch(TodoItem::isCompleted));

        updateNumberOfItemsLeft();
    }

    void processAddItemAction(AddItemAction action) {
        TodoItem newItem = new TodoItem(action.getText());

        items.add(newItem);

        inputText.push("");

        selectAllCheckbox.push(false);

        updateNumberOfItemsLeft();
    }

    void processChangeCompletedSingleItemAction(ChangeCompletedForSingleItemAction action) {
        items.stream()
                .filter(item -> item.getId().equals(action.getItemId()))
                .findAny()
                .ifPresent(item -> item.setCompleted(action.getNewState()));

        selectAllCheckbox.push(items.stream()
                .allMatch(TodoItem::isCompleted));

        updateNumberOfItemsLeft();
        updateFilterPredicate();
    }

    void processChangeFilterAction(ChangeFilterAction action) {
        filterStatus = action.getVisibilityType();
        updateFilterPredicate();
    }

    private void updateFilterPredicate() {
        filteredData.setPredicate(todoItem -> {
            switch (filterStatus) {
                case ALL:
                    return true;
                case ACTIVE:
                    return !todoItem.isCompleted();
                case COMPLETED:
                    return todoItem.isCompleted();
                default:
                    return true;
            }
        });
    }

    private void updateNumberOfItemsLeft() {
        numberOfItemsLeft.setValue(items
                .stream()
                .filter(item -> !item.isCompleted())
                .count());
    }

    public ObservableList<TodoItem> getItems() {
        return filteredData;
    }

    public EventStream<String> inputText() {
        return inputText;
    }

    public EventStream<Boolean> selectAllSelected() {
        return selectAllCheckbox;
    }

    public ReadOnlyIntegerProperty numberOfItemsLeft() {
        return numberOfItemsLeft.getReadOnlyProperty();
    }
}
