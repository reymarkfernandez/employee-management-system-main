package dev.reymark;

import dev.reymark.app.RootLoader;
import dev.reymark.data.DepartmentDAO;
import dev.sol.core.application.FXApplication;
import dev.sol.core.application.loader.FXLoaderFactory;
import dev.sol.core.registry.FXCollectionsRegister;
import dev.sol.core.registry.FXControllerRegister;
import dev.sol.core.registry.FXNodeRegister;
import dev.sol.core.scene.FXSkin;
import dev.sol.core.thread.FXThreadService;
import dev.sol.db.DBService;

public class App extends FXApplication {
    // App Registers
    public static final FXControllerRegister CONTROLLER_REGISTRY = FXControllerRegister.INSTANCE;
    public static final FXCollectionsRegister COLLECTIONS_REGISTRY = FXCollectionsRegister.INSTANCE;
    public static final FXNodeRegister NODE_REGISTRY = FXNodeRegister.INSTANCE;

    // multi-threading purposes
    public static final FXThreadService THREAD = FXThreadService.INSTANCE;



    public static final DBService DB_EMPLOYEE = DBService.INSTANCE
            .initialize("jdbc:mysql://localhost/employee?user=root&password=");

    @Override
    public void initialize() throws Exception {

        setTitle("EmployeeFX JBC");
        setSkin(FXSkin.DRACULA);

        _initialize_dataset();
        _initialize_application();

    }
    private void _initialize_dataset(){
        COLLECTIONS_REGISTRY.register("DEPARTMENT", DepartmentDAO.getDepartmentList());
    }

    private void _initialize_application() {
        RootLoader rootLoader = (RootLoader) FXLoaderFactory
                .createInstance(RootLoader.class, App.class.getResource("/dev/reymark/app/backup.fxml"))
                .addParameter("scene", applicationScene)
                .initialize();
        rootLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}