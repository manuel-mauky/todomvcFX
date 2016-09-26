package todomvcfx.mvvmfx.ui.item;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import todomvcfx.mvvmfx.model.TodoItem;

/**
 * @author manuel.mauky
 */
public class ItemViewModel implements ViewModel {
	
	private BooleanProperty completed = new SimpleBooleanProperty();
	
	private BooleanProperty editMode = new SimpleBooleanProperty();
	
	private StringProperty content = new SimpleStringProperty();
	
	private TodoItem item;
	private Runnable onDelete;

	public ItemViewModel(TodoItem item, Runnable onDelete) {
		this.item = item;
		this.onDelete = onDelete;
		content.bindBidirectional(item.textProperty());
		completed.bindBidirectional(item.completedProperty());
	}
	
	public void delete() {
		onDelete.run();
	}
	
	public StringProperty contentProperty() {
		return content;
	}
	
	public BooleanProperty completedProperty() {
		return completed;
	}
	
	public boolean isCompleted() {
		return completed.get();
	}
	
	public BooleanProperty editModeProperty() {
		return editMode;
	}
	
	public ReadOnlyBooleanProperty textStrikeThrough() {
		return completed;
	}
}
