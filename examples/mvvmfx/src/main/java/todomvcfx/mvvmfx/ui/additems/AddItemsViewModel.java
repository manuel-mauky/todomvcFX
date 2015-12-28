package todomvcfx.mvvmfx.ui.additems;

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener;
import todomvcfx.mvvmfx.model.TodoItem;
import todomvcfx.mvvmfx.model.TodoItemStore;

/**
 * @author manuel.mauky
 */
public class AddItemsViewModel implements ViewModel {
	
	
	private BooleanProperty allSelected = new SimpleBooleanProperty();
	private StringProperty newItemValue = new SimpleStringProperty("");
	
	private ReadOnlyBooleanWrapper allSelectedVisible = new ReadOnlyBooleanWrapper();
	
	public AddItemsViewModel() {
		allSelectedVisible.bind(Bindings.isEmpty(TodoItemStore.getInstance().getItems()).not());

        TodoItemStore.getInstance().getItems().addListener((ListChangeListener<TodoItem>) change ->
                Platform.runLater(() -> allSelected.setValue(
                    TodoItemStore.getInstance().getItems().stream().allMatch(TodoItem::isCompleted))));
	}

    public void selectAll() {
        TodoItemStore.getInstance().getItems().forEach(item -> item.setCompleted(allSelected.getValue()));
    }
	
	
	public void addItem() {
		final String newValue = newItemValue.get();
		if (newValue != null && !newValue.trim().isEmpty()) {
			TodoItemStore.getInstance().getItems().add(new TodoItem(newValue));
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
