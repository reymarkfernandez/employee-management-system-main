package dev.reymark.app;

import dev.reymark.App;
import dev.reymark.models.Department;
import dev.reymark.models.Employee;
import dev.reymark.models.Job;
import dev.sol.core.application.FXController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RootController extends FXController {
    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<String, Employee> empIdColumn;

    @FXML
    private TableColumn<String, Employee> empFirstnameColumn;

    @FXML
    private TableColumn<String, Employee> empLastnameColumn;

    @FXML
    private TableColumn<Job, Employee> empJobColumn;

    @FXML
    private TableColumn<Employee, Employee> empManagerColumn;

    @FXML
    private TableColumn<Department, Employee> departmentColumn;

    @FXML
    private ComboBox<Department> departmentDropdown;

    private ObservableList<Department> department_masterlist;
    private ObservableList<Employee> employee_masterlist;

    @Override
    protected void load_fields() {
        employee_masterlist = FXCollections.observableArrayList();
        department_masterlist = App.COLLECTIONS_REGISTRY.getList("DEPARTMENT");

        departmentDropdown.setButtonCell(new Department.LIST_CELL());
        departmentDropdown.setCellFactory(cell -> new Department.LIST_CELL());
        departmentDropdown.setItems(department_masterlist);

        departmentColumn.setCellFactory(cell -> new Employee.DEPARTMENT_TABLECELL());
    }

    @Override
    protected void load_bindings() {
    }

    @Override
    protected void load_listeners() {
        departmentDropdown.getSelectionModel().selectFirst();
    }
}
