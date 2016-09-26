package todomvcfx.mvvmfx.ui.additems;

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import todomvcfx.mvvmfx.model.TodoItem;
import todomvcfx.mvvmfx.model.TodoItemStore;

/**
 * @author manuel.mauky
 */
public class AddItemsViewModel implements ViewModel {
	
	
	private BooleanProperty allSelected = new SimpleBooleanProperty();
	private StringProperty newItemValue = new SimpleStringProperty("");
	
    private ReadOnlyBooleanWrapper allSelectedVisible = new ReadOnlyBooleanWrapper();

    // In contrast to the ItemStore list, this list will fire updates when the completed flag of the elements is updated.
    private ObservableList<TodoItem> todoItemsWithCompletedUpdater =
			FXCollections.observableArrayList(item -> new Observable[]{item.completedProperty()});

	private TodoItemStore store;

	public AddItemsViewModel(TodoItemStore store) {
		this.store = store;

		allSelectedVisible.bind(Bindings.isNotEmpty(store.getItems()));

        Bindings.bindContent(todoItemsWithCompletedUpdater, store.getItems());

        todoItemsWithCompletedUpdater.addListener((ListChangeListener<TodoItem>) c -> {
            while(c.next()) {
				boolean allCompleted = this.store.getItems().stream().allMatch(TodoItem::isCompleted);
				Platform.runLater(() -> allSelected.setValue(allCompleted));
			}
        });
	}

	public void selectAll() {
		Boolean newValue = allSelected.getValue();
		store.getItems().forEach(item -> item.setCompleted(newValue));
    }

	public void addItem() {
		final String newValue = newItemValue.get();
		if (newValue != null && !newValue.trim().isEmpty()) {
			store.addNewItem(new TodoItem(newValue));
			newItemValue.set("");
		}
	}
	
	public StringProperty newItemValueProperty() {
		return newItemValue;
	}

	public BooleanProperty allSelectedProperty() {
		return allSelected;
	}
	
	public ReadOnlyBooleanProperty allSelectedVisibleProperty() {
		return allSelectedVisible;
	}
}
