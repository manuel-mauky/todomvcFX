package todomvcfx.referenceimpl;

public class Repository {

    private static final Repository SINGLETON = new Repository();

    public static Repository getSingleton () {
        return SINGLETON;
    }

    private Repository () {
    }
}
