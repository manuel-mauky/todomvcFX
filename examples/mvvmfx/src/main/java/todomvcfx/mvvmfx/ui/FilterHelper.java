package todomvcfx.mvvmfx.ui;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author manuel.mauky
 */
public class FilterHelper {

	/**
	 * Creates an observable list which contains the items of the given observable list, filtered by a given filter criterion.
	 *
	 * The function is applied to all items in the list to obtain an observable boolean which is determines whether the
	 * item is included in the filtered list or not.
	 * The item will be included when the observable boolean is <code>true</code>.
	 */
	public static <T> ObservableList<T> filter(ObservableList<T> items,
			Function<T, ObservableBooleanValue> conditionExtractor) {
		return filterInternal(items, conditionExtractor, t -> conditionExtractor.apply(t).get());
	}

	/**
	 * Creates an observable list which contains the items of the given observable list, filtered by a given filter criterion.
	 *
	 * The function is applied to all items in the list to obtain an observable boolean which is determines whether the
	 * item is included in the filtered list or not.
	 * The item will be included when the observable boolean is <code>false</code>.
	 */
	public static <T> ObservableList<T> filterInverted(ObservableList<T> items,
			Function<T, ObservableBooleanValue> conditionExtractor) {
		return filterInternal(items, conditionExtractor, t -> !conditionExtractor.apply(t).get());
	}
	
	
	private static <T> ObservableList<T> filterInternal(ObservableList<T> items,
			Function<T, ObservableBooleanValue> conditionExtractor, final Predicate<T> predicate) {
		final ObservableList<T> filteredItems = FXCollections.observableArrayList();
		
		final InvalidationListener listener = observable -> {
			final List<T> completed = items.stream()
					.filter(predicate)
					.collect(Collectors.toList());
			
			filteredItems.setAll(completed);
		};
		
		items.addListener((ListChangeListener<T>) c -> {
			c.next();
			
			listener.invalidated(null);
			
			if (c.wasAdded()) {
				c.getAddedSubList()
						.forEach(item -> conditionExtractor.apply(item).addListener(listener));
			}
			
			if (c.wasRemoved()) {
				c.getRemoved()
						.forEach(item -> conditionExtractor.apply(item).removeListener(listener));
			}
		});
		
		return filteredItems;
	}
	
	
	
}
