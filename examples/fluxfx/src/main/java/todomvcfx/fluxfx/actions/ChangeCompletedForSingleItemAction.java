package todomvcfx.fluxfx.actions;

import eu.lestard.fluxfx.Action;

public class ChangeCompletedForSingleItemAction implements Action {

    private final String itemId;

    private final boolean newState;

    public ChangeCompletedForSingleItemAction(String itemId, boolean newState) {
        this.itemId = itemId;
        this.newState = newState;
    }

    public String getItemId() {
        return itemId;
    }

    public boolean getNewState() {
        return newState;
    }
}
