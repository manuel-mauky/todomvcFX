package todomvcfx.mvvmfx;

import javafx.application.Application;
import javafx.application.Platform;
import org.junit.After;
import todomvcfx.AbstractTest;
import todomvcfx.mvvmfx.model.TodoItemStore;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MvvmfxTest extends AbstractTest {
    @Override
    public Class<? extends Application> getAppClass() {
        return MvvmfxApp.class;
    }

    @After
    public void tearDown() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Platform.runLater(() -> {
            TodoItemStore.getInstance().getItems().clear();
            future.complete(null);
        });

        future.get(2, TimeUnit.SECONDS);
    }
}