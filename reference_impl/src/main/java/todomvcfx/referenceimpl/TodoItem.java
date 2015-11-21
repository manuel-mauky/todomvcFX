package todomvcfx.referenceimpl;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TodoItem {

    private StringProperty text = new SimpleStringProperty();

    private BooleanProperty done = new SimpleBooleanProperty();

    public TodoItem() {
        this("");
    }

    public TodoItem(String text) {
        this.text.setValue(text);
        this.done.setValue(false);
    }


    @Override
    public String toString() {
        return text.getValue();
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }

    public void setText(String text) {
        this.text.set(text);
    }

    public boolean isDone() {
        return done.get();
    }

    public BooleanProperty doneProperty() {
        return done;
    }

    public void setDone(boolean done) {
        this.done.set(done);
    }
}
