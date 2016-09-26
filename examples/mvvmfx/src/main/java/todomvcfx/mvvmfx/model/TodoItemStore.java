package todomvcfx.mvvmfx.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author manuel.mauky
 */
public class TodoItemStore {
	
	private ObservableList<TodoItem> items = FXCollections.observableArrayList();

	public ObservableList<TodoItem> getItems() {
		return items;
	}

	public void addNewItem(TodoItem item) {
		this.items.add(item);
	}

	public void deleteItem(TodoItem item) {
		this.items.remove(item);
	}
}
