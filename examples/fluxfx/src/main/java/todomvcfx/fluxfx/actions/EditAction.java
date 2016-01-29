package todomvcfx.fluxfx.actions;

import eu.lestard.fluxfx.Action;

public class EditAction implements Action {

    private final String itemId;

    private final String newText;

    public EditAction(String itemId, String newText) {
        this.itemId = itemId;
        this.newText = newText;
    }

    public String getItemId() {
        return itemId;
    }

    public String getNewText() {
        return newText;
    }
}
