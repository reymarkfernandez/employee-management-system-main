package dev.reymark.app;

import dev.reymark.App;
import dev.reymark.models.Department;
import dev.reymark.models.Employee;
import dev.reymark.models.Job;
import dev.sol.core.application.FXController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RootController extends FXController {
    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> empIdColumn;

    @FXML
    private TableColumn<Employee, String> empNameColumn;

    @FXML
    private TableColumn<Employee, String> empLastnameColumn;

    @FXML
    private TableColumn<Employee, Job> empJobColumn;

    @FXML
    private TableColumn<Employee, Employee> empManagerColumn;

    @FXML
    private TableColumn<Employee, Department> departmentColumn;

    @FXML
    private ComboBox<Department> departmentDropdown;

    private ObservableList<Department> department_masterlist;
    private ObservableList<Employee> employee_masterlist;

    @Override
    protected void load_fields() {
        employee_masterlist = App.COLLECTIONS_REGISTRY.getList("EMPLOYEE");
        department_masterlist = App.COLLECTIONS_REGISTRY.getList("DEPARTMENT");

        departmentDropdown.setButtonCell(new Department.LIST_CELL());
        departmentDropdown.setCellFactory(cell -> new Department.LIST_CELL());
        departmentDropdown.setItems(department_masterlist);

        empIdColumn.setCellValueFactory(cell -> cell.getValue().emp_idProperty() );
        empNameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        empJobColumn.setCellValueFactory(cell -> cell.getValue().jobProperty());


        empManagerColumn.setCellFactory(cell -> {
            TableCell<Employee, Employee> tableCell = new TableCell<>(){
                @Override
                protected void updateItem(Employee item, boolean empty){
                    super.updateItem(item, empty);

                    if (item == null || empty){
                        setText(null);
                        setGraphic(null);
                        return;
                    }

                    setGraphic(new Label(item.getName()));
                }
            };
            return tableCell;
        });
        empManagerColumn.setCellValueFactory(cell -> cell.getValue().managerpProperty());

        departmentColumn.setCellFactory(cell -> new Employee.DEPARTEMENT_TABLECELL());
        departmentColumn.setCellValueFactory(cell -> cell.getValue().departmentProperty());

        employeeTable.setItems(employee_masterlist);
    }

    @Override
    protected void load_bindings() {
    }

    @Override
    protected void load_listeners() {
        departmentDropdown.getSelectionModel().selectFirst();
    }
}
