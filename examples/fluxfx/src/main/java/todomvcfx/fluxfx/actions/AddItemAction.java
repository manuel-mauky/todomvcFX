package todomvcfx.fluxfx.actions;

import eu.lestard.fluxfx.Action;

public class AddItemAction implements Action {

    private final String text;

    public AddItemAction(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
