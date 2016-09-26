package todomvcfx.mvvmfx;

import javafx.application.Application;
import todomvcfx.AbstractTest;

public class MvvmfxTest extends AbstractTest {
    @Override
    public Class<? extends Application> getAppClass() {
        return MvvmfxApp.class;
    }
}