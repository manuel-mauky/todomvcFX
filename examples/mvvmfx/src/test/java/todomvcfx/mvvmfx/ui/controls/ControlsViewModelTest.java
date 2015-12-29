package todomvcfx.mvvmfx.ui.controls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import todomvcfx.mvvmfx.model.TodoItem;
import todomvcfx.mvvmfx.model.TodoItemStore;

import static org.assertj.core.api.Assertions.assertThat;

public class ControlsViewModelTest {

    private ControlsViewModel viewModel;

    private TodoItem item1;
    private TodoItem item2;

    private TodoItemStore itemStore = TodoItemStore.getInstance();

    @Before
    public void setup() {
        viewModel = new ControlsViewModel();

        item1 = new TodoItem("1");
        item2 = new TodoItem("2");

        itemStore.getItems().clear();
    }


    @After
    public void tearDown() {
        itemStore.getItems().clear();
    }

    @Test
    public void testItemLabel() {
        // given
        assertThat(itemStore.getItems()).isEmpty();

        // then
        assertThat(viewModel.itemsLeftLabelTextProperty().get()).isEqualTo("0 items left");


        // when
        itemStore.getItems().add(item1);

        // then
        assertThat(viewModel.itemsLeftLabelTextProperty().get()).isEqualTo("1 item left");

        // when
        itemStore.getItems().add(item2);

        // then
        assertThat(viewModel.itemsLeftLabelTextProperty().get()).isEqualTo("2 items left");


        // when
        itemStore.getItems().remove(item1);

        // then
        assertThat(viewModel.itemsLeftLabelTextProperty().get()).isEqualTo("1 item left");

    }

}
