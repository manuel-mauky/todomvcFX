package todomvcfx;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;
import todomvcfx.matcher.ButtonMatchers;
import todomvcfx.matcher.CheckBoxMatchers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ListViewMatchers.hasItems;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

public abstract class AbstractTest extends FxRobot {


    public abstract Class<? extends Application> getAppClass();


    @Before
    public void setupApp() throws Exception {
        FxToolkit.registerPrimaryStage();

        FxToolkit.setupApplication(getAppClass());
    }

    @Test
    public void testInitialState() {
        verifyThat("#items", hasItems(0));

        verifyThat("#addInput", hasText(""));

        verifyThat("#selectAll", CheckBoxMatchers.isSelected(false));
        verifyThat("#selectAll", NodeMatchers.isInvisible());

        verifyThat("#itemsLeftLabel", LabeledMatchers.hasText("0 items left"));
        verifyThat("#showAll", ButtonMatchers.isSelected(true));
        verifyThat("#showActive", ButtonMatchers.isSelected(false));
        verifyThat("#showCompleted", ButtonMatchers.isSelected(false));
    }

    @Test
    public void testAddItem() {
        // before
        verifyThat("#selectAll", NodeMatchers.isInvisible());


        clickOn("#addInput");
        write("todo1").push(KeyCode.ENTER);


        verifyThat("#items", hasItems(1));
        verifyThat("#itemsLeftLabel", LabeledMatchers.hasText("1 item left"));

        // after
        verifyThat("#selectAll", NodeMatchers.isVisible());


        write("todo2").push(KeyCode.ENTER);

        verifyThat("#items", hasItems(2));
        verifyThat("#itemsLeftLabel", LabeledMatchers.hasText("2 items left"));
    }



    @Test
    public void testSwitchEditMode() throws Exception {
        // given
        addSomeItems();

        verifyThat(getItemContentBox(1), NodeMatchers.isVisible());
        verifyThat(getItemEditTextField(1), NodeMatchers.isInvisible());



        // when
        doubleClickOn(getItemContentBox(1));

        // then
        verifyThat(getItemContentBox(1), NodeMatchers.isInvisible());
        verifyThat(getItemEditTextField(1), NodeMatchers.isVisible());

        // when
        push(KeyCode.ENTER);

        // then
        verifyThat(getItemContentBox(1), NodeMatchers.isVisible());
        verifyThat(getItemEditTextField(1), NodeMatchers.isInvisible());


        // given
        doubleClickOn(getItemContentBox(1));
        verifyThat(getItemContentBox(1), NodeMatchers.isInvisible());
        verifyThat(getItemEditTextField(1), NodeMatchers.isVisible());

        // when
        clickOn(getItemContentBox(2)); // other label

        // then
        verifyThat(getItemContentBox(1), NodeMatchers.isVisible());
        verifyThat(getItemEditTextField(1), NodeMatchers.isInvisible());
    }

    @Test
    public void testRenameItem() {
        // given
        addSomeItems();

        verifyThat(getItemLabel(1), LabeledMatchers.hasText("todo1"));

        // when
        doubleClickOn(getItemContentBox(1));
        write("other text").push(KeyCode.ENTER);

        // then
        verifyThat(getItemLabel(1), LabeledMatchers.hasText("other text"));



        // given
        verifyThat(getItemLabel(2), LabeledMatchers.hasText("todo2"));

        // when
        doubleClickOn(getItemContentBox(2));
        write("something");

        clickOn(getItemContentBox(0));

        // then
        verifyThat(getItemLabel(2), LabeledMatchers.hasText("something"));

    }

    @Test
    public void testDeleteButtonIsOnlyVisibleOnMouseOver() {
        clickOn("#addInput");
        write("todo1").push(KeyCode.ENTER);
        write("todo2").push(KeyCode.ENTER);


        // at first the delete buttons of both items are invisible
        Button delete0 = getItemDeleteButton(0);
        Button delete1 = getItemDeleteButton(1);

        assertFalse(delete0.isVisible());
        assertFalse(delete1.isVisible());


        // then we move the mouse over the second item
        Node element = getItemContentBox(1);
        moveTo(element);

        // now the delete button of the second item is visible while the first one is still invisible
        assertFalse(delete0.isVisible());
        assertTrue(delete1.isVisible());

        // after moving the mouse away from the list view both buttons are invisible agian
        moveTo("#addInput");

        assertFalse(delete0.isVisible());
        assertFalse(delete1.isVisible());
    }


    @Test
    public void testRemoveItems() {
        addSomeItems();


        // before
        verifyThat("#items", hasItems(4));
        verifyThat("#itemsLeftLabel", LabeledMatchers.hasText("4 items left"));

        clickOn(getItemDeleteButton(1), MouseButton.PRIMARY);


        // after
        verifyThat("#items", hasItems(3));
        verifyThat("#itemsLeftLabel", LabeledMatchers.hasText("3 items left"));
    }

    @Test
    public void testCompleteItems() {
        // given
        addSomeItems();
        verifyThat("#itemsLeftLabel", LabeledMatchers.hasText("4 items left"));

        // when
        clickOn(getItemCheckbox(1));

        // then
        verifyThat("#itemsLeftLabel", LabeledMatchers.hasText("3 items left"));
    }


    @Test
    public void testSwitchFilter() {
        addSomeItems();

        // given
        clickOn(getItemCheckbox(0));

        verifyThat("#items", hasItems(4));


        // when
        clickOn("#showActive");

        // then
        verifyThat("#items", hasItems(3));


        // when
        clickOn("#showCompleted");

        // then
        verifyThat("#items", hasItems(1));

        // when
        clickOn("#showAll");

        // then
        verifyThat("#items", hasItems(4));

    }

    @Test
    public void testItemsListIsUpdatedCorrectly() {
        // given
        clickOn("#addInput");
        write("todo0").push(KeyCode.ENTER);

        clickOn(getItemCheckbox(0));

        clickOn("#showCompleted");

        verifyThat("#items", hasItems(1));


        // when
        clickOn(getItemCheckbox(0));

        // then
        verifyThat("#items", hasItems(0));

    }

    @Test
    public void testSelectAllCheckboxClicked() {
        // given
        addSomeItems();

        verifyThat(getItemCheckbox(0), CheckBoxMatchers.isSelected(false));
        verifyThat(getItemCheckbox(1), CheckBoxMatchers.isSelected(false));
        verifyThat(getItemCheckbox(2), CheckBoxMatchers.isSelected(false));
        verifyThat(getItemCheckbox(3), CheckBoxMatchers.isSelected(false));

        // when
        clickOn("#selectAll");

        // then
        verifyThat(getItemCheckbox(0), CheckBoxMatchers.isSelected(true));
        verifyThat(getItemCheckbox(1), CheckBoxMatchers.isSelected(true));
        verifyThat(getItemCheckbox(2), CheckBoxMatchers.isSelected(true));
        verifyThat(getItemCheckbox(3), CheckBoxMatchers.isSelected(true));


        // when
        clickOn("#selectAll");

        // then
        verifyThat(getItemCheckbox(0), CheckBoxMatchers.isSelected(false));
        verifyThat(getItemCheckbox(1), CheckBoxMatchers.isSelected(false));
        verifyThat(getItemCheckbox(2), CheckBoxMatchers.isSelected(false));
        verifyThat(getItemCheckbox(3), CheckBoxMatchers.isSelected(false));
    }



    /**
     * When all items are completed, the global "selectAll" checkbox has
     * to be switched to checked too.
     * On the other hand the selectAll checkbox has to switch back to unchecked when
     * at least one item isn't completed.
     */
    @Test
    public void testSelectAllCheckboxBehaviourWhenItemsCompleted() {
        // given
        addSomeItems();
        verifyThat("#selectAll", CheckBoxMatchers.isSelected(false));

        // when
        clickOn(getItemCheckbox(0));
        clickOn(getItemCheckbox(1));
        clickOn(getItemCheckbox(2));
        clickOn(getItemCheckbox(3));

        // then
        verifyThat("#selectAll", CheckBoxMatchers.isSelected(true));


        // when
        clickOn(getItemCheckbox(0));

        // then
        verifyThat("#selectAll", CheckBoxMatchers.isSelected(false));


        // when
        clickOn(getItemDeleteButton(0));

        // then
        verifyThat("#selectAll", CheckBoxMatchers.isSelected(true));
    }

    @Test
    public void testSelectAllIsInvisibleAfterLastItemIsRemoved() {
        // given
        clickOn("#addInput");
        write("todo0").push(KeyCode.ENTER);
        write("todo1").push(KeyCode.ENTER);
        verifyThat("#selectAll", NodeMatchers.isVisible());

        // when
        clickOn(getItemDeleteButton(1));
        clickOn(getItemDeleteButton(0));

        // then
        verifyThat("#selectAll", NodeMatchers.isInvisible());
    }


    @Test
    public void testSelectAllSwitchesToUncheckWhenNewItemIsAdded() {
        // given
        addSomeItems();

        clickOn("#selectAll");
        verifyThat("#selectAll", CheckBoxMatchers.isSelected(true));


        // when
        clickOn("#addInput");
        write("todo5").push(KeyCode.ENTER);

        // then
        verifyThat("#selectAll", CheckBoxMatchers.isSelected(false));
    }



    // HELPERS

    private TextField getItemEditTextField(int index) {
        return (TextField) lookup("#items").lookup("#contentInput").selectAt(index).queryFirst();
    }

    private Label getItemLabel(int index) {
        return (Label) lookup("#items").lookup("#contentLabel").selectAt(index).queryFirst();
    }



    private Node getItemContentBox(int index) {
        return lookup("#items").lookup("#contentBox").selectAt(index).queryFirst();
    }

    private CheckBox getItemCheckbox(int index) {
        return (CheckBox)lookup("#items").lookup("#completed").selectAt(index).queryFirst();
    }

    private Button getItemDeleteButton(int index) {
        return (Button)lookup("#items").lookup("#deleteButton").selectAt(index).queryFirst();
    }


    private void addSomeItems() {
        clickOn("#addInput");
        write("todo0").push(KeyCode.ENTER);
        write("todo1").push(KeyCode.ENTER);
        write("todo2").push(KeyCode.ENTER);
        write("todo3").push(KeyCode.ENTER);

        verifyThat("#items", hasItems(4));
    }

}
