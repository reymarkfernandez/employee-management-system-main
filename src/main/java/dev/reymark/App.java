package dev.reymark;

import dev.reymark.app.RootLoader;
import dev.sol.core.application.FXApplication;
import dev.sol.core.application.loader.FXLoaderFactory;
import dev.sol.core.scene.FXSkin;

public class App extends FXApplication {

    @Override
    public void initialize() throws Exception {

        setTitle("EmployeeFX JBC");
        setSkin(FXSkin.DRACULA);
        applicationStage.setResizable(false);
        _initialize_application();
    }

    private void _initialize_application() {
        RootLoader rootLoader = (RootLoader) FXLoaderFactory
                .createInstance(RootLoader.class, App.class.getResource("/dev/reymark/app/ROOT.fxml"))
                .addParameter("scene", applicationScene).initialize();
        rootLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}