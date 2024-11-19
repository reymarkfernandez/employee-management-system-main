package dev.reymark.models;

import java.time.LocalDate;
import dev.sol.core.application.FXModel;
import dev.sol.core.properties.beans.FXLongProperty;
import dev.sol.core.properties.beans.FXStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import dev.sol.core.properties.beans.FXObjectProperty;

public class Employee extends FXModel {
    public static class DEPARTMENT_TABLECELL extends TableCell<Department, Employee> {
        @Override
        protected void updateItem(Employee item, boolean empty) {
            super.updateItem(item, empty);

            if (item == null || empty) {
                setText(null);
                setGraphic(null);
                // setGraphic()
                return;
            }

            setGraphic(new Label(item.getDepartment().getName()));
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
        this(emp_id,  job, manager, hireDate, null, null, Department);
    }

    public Employee(String emp_id, String name, Job job, Employee employee, LocalDate hire_date, Long salary,
            Long commission, Department department) {
        this.emp_id = new FXStringProperty(emp_id);
        this.name = new FXStringProperty(name);
        this.job = new FXObjectProperty<>(job);
        this.manager = new FXObjectProperty<>();
        this.hire_date = new FXObjectProperty<>(hire_date);
        this.salary = new FXLongProperty(salary);
        this.commission = new FXLongProperty(commission);
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

    public Employee(String id, String name2, Job job2, LocalDate hireDate, long salary2, long commission2,
            Department department2) {
        //TODO Auto-generated constructor stub
    }

    public FXStringProperty empIdProperty() {
        return emp_id;

    }

    public String getEmpId() {
        return empIdProperty().get();

    }

    public void setEmpId(String emp_id) {
        empIdProperty().set(emp_id);

    }

    public FXStringProperty nameProperty() {
        return name;

    }

    public String getName() {
        return nameProperty().get();

    }

    public void setName(String name) {
        nameProperty().set(name);

    }

    public FXObjectProperty<Job> jobProperty() {
        return job;

    }

    public Job getJob() {
        return jobProperty().get();

    }

    public void setJob(Job job) {
        jobProperty().set(job);
    }

    public FXObjectProperty<Employee> managerProperty() {
        return manager;

    }

    public Employee getManager() {
        return managerProperty().get();

    }

    public void setManager(Employee manager) {
        managerProperty().set(manager);
    }

    public FXObjectProperty<LocalDate> hire_dateProperty() {
        return hire_date;

    }

    public LocalDate getHireDate() {
        return hire_dateProperty().get();

    }

    public void setHireDate(LocalDate hire_Date) {
        hire_dateProperty().set(hire_Date);
    }

    public FXLongProperty salaryProperty() {
        return salary;

    }

    public long getSalary() {
        return salaryProperty().get();

    }

    public void setSalary(long salary) {
        salaryProperty().set(salary);
    }

    public FXLongProperty commissionProperty() {
        return commission;

    }

    public long getCommision() {
        return commissionProperty().get();

    }

    public void setCommission(long commission) {
        commissionProperty().set(commission);
    }

    public FXObjectProperty<Department> departmentProperty() {
        return department;

    }

    public Department getDepartment() {
        return departmentProperty().get();
    }

    public void setDepartment(Department department) {
        departmentProperty().set(department);
    }

    @Override
    public FXModel clone() {
        Employee employee = new Employee(getEmpId(), getName(), getJob(), getManager(), getHireDate(), getSalary(),
                getCommision(), getDepartment());
        if (getManager() != null)
            employee.setManager(getManager());
        return employee;
    }

    @Override
    public void copy(FXModel arg0) {
        Employee c = (Employee) arg0;

        setEmpId(c.getEmpId());
        setName(c.getName());
        setJob(c.getJob());
        setManager(c.getManager());
        setHireDate(c.getHireDate());
        setDepartment(c.getDepartment());
        setSalary(c.getSalary());
        setCommission(c.getCommision());
    }
}
