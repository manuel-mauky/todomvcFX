package todomvcfx.mvvmfx;

import de.saxsys.mvvmfx.FluentViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import todomvcfx.mvvmfx.ui.MainView;

/**
 * @author manuel.mauky
 */
public class MvvmfxApp extends Application {
	
	public static void main(String... args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("TodoMVVM");

		final Parent parent = FluentViewLoader.fxmlView(MainView.class).load().getView();
        final Scene scene = new Scene(parent);

        stage.setScene(scene);
		stage.show();
	}
}
