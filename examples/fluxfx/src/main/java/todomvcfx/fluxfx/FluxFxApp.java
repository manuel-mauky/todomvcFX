package todomvcfx.fluxfx;

import eu.lestard.easydi.EasyDI;
import eu.lestard.fluxfx.ViewLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import todomvcfx.fluxfx.views.MainView;


public class FluxFxApp extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        EasyDI context = new EasyDI();
        ViewLoader.setDependencyInjector(context::getInstance);

        final Parent parent = ViewLoader.load(MainView.class);

        stage.setScene(new Scene(parent));
        stage.show();
    }
}
