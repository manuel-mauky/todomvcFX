package todomvcfx.fluxfx.stores;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import java.util.UUID;

/**
 * @author manuel.mauky
 */
public class TodoItem {

    private final String id;

	private ReadOnlyStringWrapper text = new ReadOnlyStringWrapper();
    private ReadOnlyBooleanWrapper completed = new ReadOnlyBooleanWrapper(false);

    public TodoItem(String text) {
        this.text.set(text);

        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text.getValue();
    }

    void setText(String text) {
        this.text.setValue(text);
    }

    public ReadOnlyStringProperty textProperty() {
        return text.getReadOnlyProperty();
    }

    public boolean isCompleted() {
        return completed.getValue();
    }

    void setCompleted(boolean completed) {
        this.completed.setValue(completed);
    }

    public ReadOnlyBooleanProperty completedProperty() {
        return completed.getReadOnlyProperty();
    }

}
