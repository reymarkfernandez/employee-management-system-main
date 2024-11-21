package dev.reymark.app;

import java.util.Collections;
import java.util.Comparator;

import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import dev.reymark.App;
import dev.reymark.data.EmployeeDAO;
import dev.reymark.models.Department;
import dev.reymark.models.Employee;
import dev.reymark.models.Job;
import dev.sol.core.application.FXController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RootController extends FXController {
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, String> empIdColumn;
    @FXML
    private TableColumn<Employee, String> empNameColumn;
    @FXML
    private TableColumn<Employee, Job> empJobColumn;
    @FXML
    private TableColumn<Employee, Employee> empManagerColumn;
    @FXML
    private TableColumn<Employee, Department> departmentColumn;

    @FXML
    private ComboBox<Department> departmentField;
    @FXML
    private TextField nameField;
    @FXML
    private ComboBox<Employee> managerField;
    @FXML
    private ComboBox<Job> jobField;
    @FXML
    private TextField filteredEmployeeField;
    @FXML
    ComboBox<Employee> newManagerField;

    @FXML
    private void handleAddEmployee() {
        if (nameField.getText().isEmpty()) {
            nameField.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            Animations.flash(nameField).playFromStart();
            return;
        }
        Collections.sort(employee_masterList, Comparator.comparing(Employee::getEmpID));
        int id_int = Integer.parseInt(employee_masterList.getLast().getEmpID()) + 1;
        String emp_id = Integer.toString(id_int);
        Employee employee = new Employee(emp_id,
                nameField.getText(),
                jobField.getValue(),
                managerField.getValue(),
                departmentField.getValue());
        EmployeeDAO.insert(employee);
        employee_masterList.add(employee);
        reset_newEmployeeField();
    }

    @FXML
    private void handleSearchEmployee() {
        if (filteredEmployeeField.getText().isEmpty()) {
            filteredEmployeeField.pseudoClassStateChanged(Styles.STATE_DANGER, true);
            Animations.flash(filteredEmployeeField).playFromStart();
            return;
        }

        employeeFilteredList.setPredicate(employee -> {
            return employee.getEmpID().trim().toUpperCase().contains(filteredEmployeeField.getText());
        });
    }

    @FXML
    private void handleSearchAllEmployee() {
        employeeFilteredList.setPredicate(p -> true);
    }

    @FXML
    private void handleDeleEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Employe Delete Error");
            alert.setHeaderText("Null Selection Error Occcured");
            alert.setContentText("No employee selected from table. Must select employee to delete");
            alert.initOwner(scene.getWindow());
            alert.show();
            return;

        }

        if (employee_masterList.stream().anyMatch(e -> {
            return e.getManager().getEmpID().equals(selectedEmployee.getEmpID());
        })) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Employe Delete Error");
            alert.setHeaderText("Employee Currently in Use");
            alert.setContentText("Employee is used as manager. Must delete employees under management first.");
            alert.initOwner(scene.getWindow());
            alert.show();
            return;

        }
        employee_masterList.remove(selectedEmployee);
        EmployeeDAO.delete(selectedEmployee);
    }

    @FXML
    private void handleUpdateEmployee() {
        Employee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Employe Delete Error");
            alert.setHeaderText("Null Selection Error Occcured");
            alert.setContentText("No employee selected from table. Must select employee to delete");
            alert.initOwner(scene.getWindow());
            alert.show();
            return;
        } 
        
        selectedEmployee.setManager(newManagerField.getValue());
        EmployeeDAO.update(selectedEmployee);

    }

    private Scene scene;

    private ObservableList<Department> department_masterList;
    private ObservableList<Employee> employee_masterList;

    private FilteredList<Employee> managerList;
    private FilteredList<Employee> employeeFilteredList;

    private static class MANAGER_CELL extends ListCell<Employee> {
        @Override
        protected void updateItem(Employee item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setGraphic(new Label("SELECT MANAGER"));
                return;
            }
            setGraphic(new Label(item.getName()));
        }

    }

    @Override
    protected void load_fields() {
        scene = (Scene) getParameter("SCENE");
        employee_masterList = App.COLLECTIONS_REGISTRY.getList("EMPLOYEE");

        department_masterList = App.COLLECTIONS_REGISTRY.getList("DEPARTMENT");
        employeeFilteredList = new FilteredList<>(employee_masterList, p -> true);

        managerList = new FilteredList<>(employee_masterList, employeee -> {
            return employeee.getJob() == Job.PRESIDENT || employeee.getJob() == Job.MANAGER;
        });

        managerField.setButtonCell(new MANAGER_CELL());
        managerField.setCellFactory(cell -> new MANAGER_CELL());
        managerField.setItems(managerList);

        newManagerField.setButtonCell(new MANAGER_CELL());
        newManagerField.setCellFactory(cell -> new MANAGER_CELL());
        newManagerField.getItems().add(null);
        newManagerField.getItems().addAll(managerList);

        ObservableList<Job> joblList = FXCollections.observableArrayList(Job.values());
        if (employee_masterList.stream().anyMatch(e -> e.getJob().equals(Job.PRESIDENT))) {
            jobField.setItems(FXCollections.observableArrayList(joblList.subList(1, joblList.size())));
        } else
            jobField.setItems(joblList);

        departmentField.setButtonCell(new Department.LIST_CELL());
        departmentField.setCellFactory(cell -> new Department.LIST_CELL());
        departmentField.setItems(department_masterList);

        empIdColumn.setCellValueFactory(cell -> cell.getValue().emp_idProperty());
        empNameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
        empJobColumn.setCellValueFactory(cell -> cell.getValue().jobProperty());

        empManagerColumn.setCellFactory(cell -> {
            TableCell<Employee, Employee> tableCell = new TableCell<>() {
                @Override
                protected void updateItem(Employee item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
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

        employeeTable.setItems(employeeFilteredList);
    }

    @Override
    protected void load_bindings() {
    }

    @Override
    protected void load_listeners() {
        reset_newEmployeeField();

        managerField.getSelectionModel().selectFirst();
        jobField.getSelectionModel().selectFirst();
        departmentField.getSelectionModel().selectFirst();
        newManagerField.getSelectionModel().selectFirst();

        nameField.textProperty().addListener((o, ov, nv) -> {
            nameField.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        });
        filteredEmployeeField.textProperty().addListener((o, ov, nv) -> {
            filteredEmployeeField.pseudoClassStateChanged(Styles.STATE_DANGER, false);
        });
        newManagerField.valueProperty().addListener((o, ov, nv) -> {
          newManagerField.pseudoClassStateChanged(Styles.STATE_DANGER, false);

        });

        employeeTable.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> {
          newManagerField.setValue(nv.getManager());
        });
    
    }

    private void reset_newEmployeeField() {
        nameField.setText("");
        jobField.getSelectionModel().selectFirst();
        managerField.getSelectionModel().selectFirst();
        departmentField.getSelectionModel().selectFirst();

    }
}