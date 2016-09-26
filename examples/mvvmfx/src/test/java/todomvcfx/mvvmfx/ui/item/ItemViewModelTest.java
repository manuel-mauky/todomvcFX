package todomvcfx.mvvmfx.ui.item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import todomvcfx.mvvmfx.model.TodoItem;
import todomvcfx.mvvmfx.model.TodoItemStore;

import static eu.lestard.assertj.javafx.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemViewModelTest {

    private ItemViewModel viewModel;

    private TodoItem item;

    private TodoItemStore itemStore;

    @Before
    public void setup() {
        itemStore = new TodoItemStore();

        item = new TodoItem("test");

        viewModel = new ItemViewModel(item, () -> itemStore.deleteItem(item));
        itemStore.getItems().clear();
    }


    @After
    public void tearDown() {
        itemStore.getItems().clear();
    }

    @Test
    public void testBindings() {
        // given
        assertThat(viewModel.contentProperty()).hasSameValue(item.textProperty());
        assertThat(viewModel.completedProperty()).hasSameValue(item.completedProperty());

        // when
        item.setText("other value");
        item.setCompleted(true);

        // then
        assertThat(viewModel.contentProperty()).hasSameValue(item.textProperty());
        assertThat(viewModel.completedProperty()).hasSameValue(item.completedProperty());
    }

    @Test
    public void delete() {
        // given
        itemStore.getItems().add(item);

        // when
        viewModel.delete();

        // then
        assertThat(itemStore.getItems()).isEmpty();
    }

}
