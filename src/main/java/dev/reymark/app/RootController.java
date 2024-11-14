package dev.reymark.app;

import dev.reymark.models.Employee;
import dev.reymark.models.Job;
import dev.sol.core.application.FXController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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


    private ObservableList<Employee> employee_department;
    private ObservableList<Employee> employee_masterlist;



    @Override
    protected void load_fields() {
        employee_masterlist = FXCollections.observableArrayList();
        employee_department = FXCollections.observableArrayList();
      
    }

    @Override
    protected void load_bindings() {
      
    }
    @Override
    protected void load_listeners() {
       
    }

}
