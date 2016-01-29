package todomvcfx.fluxfx.views.controls;

import eu.lestard.fluxfx.View;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import todomvcfx.fluxfx.actions.ChangeFilterAction;
import todomvcfx.fluxfx.stores.ItemsStore;

public class ControlsView implements View {

    @FXML
    public Label itemsLeftLabel;


    private final ItemsStore itemsStore;

    public ControlsView(ItemsStore itemsStore) {
        this.itemsStore = itemsStore;
    }

    public void initialize(){
        itemsLeftLabel.textProperty().bind(Bindings.createStringBinding(() -> {
            final int no = itemsStore.numberOfItemsLeft().get();
            if(no == 1) {
                return no + " item left";
            } else {
                return no + " items left";
            }
        }, itemsStore.numberOfItemsLeft()));
    }

    public void all() {
        publishAction(new ChangeFilterAction(ChangeFilterAction.VisibilityType.ALL));
    }

    public void active() {
        publishAction(new ChangeFilterAction(ChangeFilterAction.VisibilityType.ACTIVE));
    }

    public void completed() {
        publishAction(new ChangeFilterAction(ChangeFilterAction.VisibilityType.COMPLETED));
    }
}
