package todomvcfx.matcher;

import javafx.scene.control.CheckBox;
import org.hamcrest.Matcher;
import org.hamcrest.Factory;
import org.testfx.matcher.base.GeneralMatchers;

public class CheckBoxMatchers {




    @Factory
    public static Matcher<CheckBox> isSelected(boolean selected) {
        String descriptionText = selected ? "is selected" : "is not selected";

        return GeneralMatchers.typeSafeMatcher(CheckBox.class, descriptionText, (checkBox) -> checkBox.isSelected() == selected);
    }


    @Factory
    public static Matcher<CheckBox> isSelected() {
        return isSelected(true);
    }

    @Factory
    public static Matcher<CheckBox> isNotSelected() {
        return isSelected(false);
    }


}
