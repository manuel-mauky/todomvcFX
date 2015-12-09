package todomvcfx.matcher;

import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import static org.testfx.matcher.base.GeneralMatchers.typeSafeMatcher;


public class ButtonMatchers {

    @Factory
    public static Matcher<ButtonBase> isPressed(boolean pressed) {
        String descriptionText = pressed ? "is pressed" : "is not pressed";

        return typeSafeMatcher(ButtonBase.class, descriptionText, button -> button.isPressed() == pressed);
    }

    public static Matcher<ToggleButton> isSelected(boolean selected) {
        String descriptionText = selected ? "is selected" : "is not selected";

        return typeSafeMatcher(ToggleButton.class, descriptionText, button -> button.isSelected() == selected);
    }
}
