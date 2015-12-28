package todomvcfx.mvvmfx.ui.item;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.utils.viewlist.CachedViewModelCellFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * @author manuel.mauky
 */
public class ItemOverviewView implements FxmlView<ItemOverviewViewModel> {

	@FXML
	public ListView<ItemViewModel> items;

	@InjectViewModel
	private ItemOverviewViewModel viewModel;

	public void initialize() {
		items.setItems(viewModel.itemsProperty());

		items.setCellFactory(CachedViewModelCellFactory.createForFxmlView(ItemView.class));
	}

}
