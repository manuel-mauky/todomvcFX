package todomvcfx.referenceimpl;

import eu.lestard.easydi.EasyDI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class ReferenceApp extends Application {

    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        EasyDI context = new EasyDI();

        final String pathToMainFxml = "/Main.fxml";

        final URL mainFxmlUrl = this.getClass().getResource(pathToMainFxml);

        if(mainFxmlUrl == null) {
            throw new IllegalStateException("Can't find Main.fxml file with path: " + pathToMainFxml);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(mainFxmlUrl);

        fxmlLoader.setControllerFactory(context::getInstance);

        fxmlLoader.load();


        Parent root = fxmlLoader.getRoot();


        stage.setScene(new Scene(root));
        stage.show();
    }
}
