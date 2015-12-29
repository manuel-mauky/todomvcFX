package todomvcfx.mvvmfx.ui.additems;

import de.saxsys.mvvmfx.ViewModel;
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
import org.fxmisc.easybind.EasyBind;
import todomvcfx.mvvmfx.model.TodoItem;
import todomvcfx.mvvmfx.model.TodoItemStore;

/**
 * @author manuel.mauky
 */
public class AddItemsViewModel implements ViewModel {
	
	
	private BooleanProperty allSelected = new SimpleBooleanProperty();
	private StringProperty newItemValue = new SimpleStringProperty("");
	
    private ReadOnlyBooleanWrapper allSelectedVisible = new ReadOnlyBooleanWrapper();

    private boolean itemsListenerActive = false;

	public AddItemsViewModel() {
		allSelectedVisible.bind(Bindings.isEmpty(TodoItemStore.getInstance().getItems()).not());


        // create a list that will fire update events when completed flag is updated.
        ObservableList<TodoItem> items = FXCollections.observableArrayList(item -> new Observable[]{item.completedProperty()});
        EasyBind.listBind(items, TodoItemStore.getInstance().getItems());

        items.addListener((ListChangeListener<TodoItem>) change -> {
            if(itemsListenerActive) {
                allSelected.setValue(
                        TodoItemStore.getInstance().getItems().stream().allMatch(TodoItem::isCompleted));
            }
        });

        itemsListenerActive = true;
	}

    public void selectAll() {
        itemsListenerActive = false;
        allSelected.setValue(!allSelected.get());
        TodoItemStore.getInstance().getItems().forEach(item -> item.setCompleted(allSelected.getValue()));
        itemsListenerActive = true;
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
