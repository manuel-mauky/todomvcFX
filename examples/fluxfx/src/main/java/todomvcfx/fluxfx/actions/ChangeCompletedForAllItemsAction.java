package todomvcfx.fluxfx.actions;

import eu.lestard.fluxfx.Action;

public class ChangeCompletedForAllItemsAction implements Action {

    private final boolean newState;

    public ChangeCompletedForAllItemsAction(boolean newState) {
        this.newState = newState;
    }

    public boolean isNewState() {
        return newState;
    }
}
