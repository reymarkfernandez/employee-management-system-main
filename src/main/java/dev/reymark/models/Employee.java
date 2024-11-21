package dev.reymark.models;

import java.time.LocalDate;

import dev.sol.core.application.FXModel;
import dev.sol.core.properties.beans.FXLongProperty;
import dev.sol.core.properties.beans.FXObjectProperty;
import dev.sol.core.properties.beans.FXStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;

public class Employee extends FXModel {

    public static class DEPARTEMENT_TABLECELL extends TableCell<Employee, Department> {
        @Override
        protected void updateItem(Department item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
                setGraphic(null);
                return;

            }

            setGraphic(new Label(item.getName()));
        }

    }

    private FXStringProperty emp_id;
    private FXStringProperty name;
    private FXObjectProperty<Job> job;
    private FXObjectProperty<Employee> manager;
    private FXObjectProperty<LocalDate> hire_date;
    private FXLongProperty salary;
    private FXLongProperty commission;
    private FXObjectProperty<Department> department;

    public Employee(String emp_id) {
        this(emp_id, null, null, null, null, 0, 0, null);
    }

    public Employee(String emp_id, String name, Job job, Employee manager, Department department) {
        this(emp_id, name, job, manager, LocalDate.now(), 0, 0, department);
    }

    public Employee(String emp_id, String name, Job job, Employee manager, LocalDate hire_date, long salary,
            long commision,
            Department department) {
        this.emp_id = new FXStringProperty(emp_id);
        this.name = new FXStringProperty(name);
        this.job = new FXObjectProperty<>(job);
        this.manager = new FXObjectProperty<>(manager);
        this.hire_date = new FXObjectProperty<>(hire_date);
        this.salary = new FXLongProperty(salary);
        this.commission = new FXLongProperty(commision);
        this.department = new FXObjectProperty<>(department);

        track_properties(this.emp_id,
                this.name,
                this.job,
                this.manager,
                this.hire_date,
                this.salary,
                this.commission,
                this.department);
    }

    public FXStringProperty emp_idProperty() {
        return emp_id;
    }

    public String getEmpID() {
        return emp_idProperty().get();
    }

    public void setEmpId(String EmpID) {
        emp_idProperty().set(EmpID);
    }

    public FXStringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return nameProperty().get();
    }

    public void setName(String Name) {
        nameProperty().set(Name);
    }

    public FXObjectProperty<Job> jobProperty() {
        return job;
    }

    public Job getJob() {
        return jobProperty().get();
    }

    public void setJob(Job Job) {
        jobProperty().set(Job);
    }

    public FXObjectProperty<Employee> managerpProperty() {
        return manager;
    }

    public Employee getManager() {
        return managerpProperty().get();
    }

    public void setManager(Employee Manager) {
        managerpProperty().set(Manager);
    }

    public FXObjectProperty<LocalDate> hire_dateProperty() {
        return hire_date;
    }

    public LocalDate getHireDate() {
        return hire_dateProperty().get();
    }

    public void setHireDate(LocalDate Hiredate) {
        hire_dateProperty().set(Hiredate);
    }

    public FXLongProperty salaryProperty() {
        return salary;
    }

    public long getSalary() {
        return salaryProperty().get();
    }

    public void setSalary(long Salary) {
        salaryProperty().set(Salary);
    }

    public FXLongProperty commissionProperty() {
        return commission;
    }

    public long getCommission() {
        return commissionProperty().get();
    }

    public void setCommission(long Commission) {
        commissionProperty().set(Commission);
    }

    public FXObjectProperty<Department> departmentProperty() {
        return department;
    }

    public Department getDepartment() {
        return departmentProperty().get();
    }

    public void setDepartment(Department Department) {
        departmentProperty().set(getDepartment());
    }

    @Override
    public FXModel clone() {
        Employee employee = new Employee(getEmpID(), getName(), getJob(), getManager(), getHireDate(), getSalary(),
                getCommission(), getDepartment());
        if (getManager() != null)
            employee.setManager(getManager());

        return employee;
    }

    @Override
    public void copy(FXModel arg0) {
        Employee c = (Employee) arg0;

        setEmpId(c.getEmpID());
        setName(c.getName());
        setJob(c.getJob());
        setManager(c.getManager());
        setHireDate(c.getHireDate());
        setDepartment(c.getDepartment());
        setSalary(c.getSalary());
        setCommission(c.getCommission());
    }

}