package dev.reymark.app;

import dev.reymark.App;
import dev.sol.core.application.loader.FXLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class RootLoader extends FXLoader {

    @Override
    public void load() {
        Scene scene = (Scene) params.get("scene");

        try {
            Parent root = loader.load();
            scene.setRoot(root);

            RootController controller = loader.getController();
            App.CONTROLLER_REGISTRY.register("ROOT", controller);
            controller.load();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
