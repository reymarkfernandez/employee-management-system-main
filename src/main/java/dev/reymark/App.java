package dev.reymark;

import dev.reymark.app.RootLoader;
import dev.reymark.data.DepartmentDAO;
import dev.reymark.data.EmployeeDAO;
import dev.sol.core.application.FXApplication;
import dev.sol.core.application.loader.FXLoaderFactory;
import dev.sol.core.registry.FXCollectionsRegister;
import dev.sol.core.registry.FXControllerRegister;
import dev.sol.core.registry.FXNodeRegister;
import dev.sol.core.scene.FXSkin;
import dev.sol.core.thread.FXThreadService;
import dev.sol.db.DBService;
import javafx.collections.FXCollections;

/**
 * JavaFX App
 */
public class App extends FXApplication {

    public static final FXControllerRegister CONTROLLER_REGISTRY = FXControllerRegister.INSTANCE;
    public static final FXCollectionsRegister COLLECTIONS_REGISTRY = FXCollectionsRegister.INSTANCE;
    public static final FXNodeRegister NODE_REGISTER = FXNodeRegister.INSTANCE;

    public static final FXThreadService THREAD_SERVICE = FXThreadService.INSTANCE;

    public static final DBService DB_EMPLOYEE = DBService.INSTANCE
            .initialize("jdbc:mysql://localhost/employee?user=root&passswords=");

    @Override
    public void initialize() throws Exception {
        setTitle("Employee ni Reymark");
        setSkin(FXSkin.DRACULA);

        _initialize_dataset();
        _initialize_application();
    }

    private void _initialize_dataset() {
        COLLECTIONS_REGISTRY.register("DEPARTMENT", FXCollections.observableArrayList(DepartmentDAO.getDepartmentList()));
        COLLECTIONS_REGISTRY.register("EMPLOYEE", FXCollections.observableArrayList(EmployeeDAO.getEmployeeList()));
    }

    private void _initialize_application() {
        RootLoader rootLoader = (RootLoader) FXLoaderFactory
                .createInstance(
                        RootLoader.class,
                        App.class.getResource("/dev/reymark/app/backup.fxml"))
                .addParameter("scene", applicationScene)
                .initialize();
        rootLoader.load();

    }
}