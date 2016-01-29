package todomvcfx.fluxfx.actions;

import eu.lestard.fluxfx.Action;

public class DeleteItemAction implements Action {

    private final String id;

    public DeleteItemAction(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
