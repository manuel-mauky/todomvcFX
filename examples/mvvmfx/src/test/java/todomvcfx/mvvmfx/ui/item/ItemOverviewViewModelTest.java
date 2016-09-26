package todomvcfx.mvvmfx.ui.item;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.utils.notifications.NotificationCenter;
import org.junit.Before;
import org.junit.Test;
import todomvcfx.mvvmfx.model.TodoItem;
import todomvcfx.mvvmfx.model.TodoItemStore;

import static org.assertj.core.api.Assertions.assertThat;

public class ItemOverviewViewModelTest {


    private ItemOverviewViewModel viewModel;

    private final NotificationCenter notificationCenter = MvvmFX.getNotificationCenter();

    private TodoItem item1;
    private TodoItem item2;
    private TodoItem item3;

    private TodoItemStore itemStore;

    @Before
    public void setup() {
        itemStore = new TodoItemStore();
        viewModel = new ItemOverviewViewModel(itemStore);

        item1 = new TodoItem("1");
        item2 = new TodoItem("2");
        item3 = new TodoItem("3");

        itemStore.getItems().clear();
    }

    @Test
    public void testAddItems() {
        // given
        assertThat(viewModel.itemsProperty()).isEmpty();

        // when
        itemStore.getItems().add(item1);

        // then
        assertThat(viewModel.itemsProperty()).hasSize(1);
        final ItemViewModel itemViewModel1 = viewModel.itemsProperty().get(0);

        assertThat(itemViewModel1.contentProperty().get()).isEqualTo(item1.getText());
    }

    @Test
    public void testRemoveItems() {
        // given
        itemStore.getItems().add(item1);
        itemStore.getItems().add(item2);
        itemStore.getItems().add(item3);

        assertThat(viewModel.itemsProperty()).hasSize(3);

        // when
        itemStore.getItems().remove(item2);

        // then
        assertThat(viewModel.itemsProperty()).hasSize(2);
    }


    @Test
    public void testSwitchCompleted() {
        // given
        itemStore.getItems().add(item1);
        itemStore.getItems().add(item2);
        itemStore.getItems().add(item3);

        assertThat(viewModel.itemsProperty()).hasSize(3);

        final ItemViewModel itemViewModel1 = viewModel.itemsProperty().get(0);
        final ItemViewModel itemViewModel2 = viewModel.itemsProperty().get(1);
        final ItemViewModel itemViewModel3 = viewModel.itemsProperty().get(2);

        item1.setCompleted(true);
        item2.setCompleted(true);


        assertThat(viewModel.itemsProperty()).hasSize(3).containsOnly(itemViewModel1, itemViewModel2, itemViewModel3);


        // when
        notificationCenter.publish("showCompleted");

        // then
        assertThat(viewModel.itemsProperty()).hasSize(2).containsOnly(itemViewModel1, itemViewModel2);

        // when
        notificationCenter.publish("showActive");

        // then
        assertThat(viewModel.itemsProperty()).hasSize(1).containsOnly(itemViewModel3);


        // when
        notificationCenter.publish("showAll");

        // then
        assertThat(viewModel.itemsProperty()).hasSize(3).containsOnly(itemViewModel1, itemViewModel2, itemViewModel3);

    }



}
