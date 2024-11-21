package dev.reymark.data;

import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import dev.reymark.App;
import dev.reymark.models.Department;
import dev.reymark.models.Employee;
import dev.reymark.models.Job;
import dev.sol.db.DBParam;
import dev.sol.db.DBService;
import dev.sol.util.CoreDateUtils;
import javafx.collections.ObservableList;

public class EmployeeDAO {
    public static final String TABLE = "employees";
    public static final DBService DB = App.DB_EMPLOYEE;

    private static final ObservableList<Department> departmentlist = App.COLLECTIONS_REGISTRY.getList("DEPARTMENT");

    private static Employee data(CachedRowSet crs) {
        try {
            String id = crs.getString("emp_id");
            String name = crs.getString("emp_name");
            Job job = Job.valueOf(crs.getString("job_name").toUpperCase().trim());
            Employee manager = new Employee(crs.getString("manager_id"));
            LocalDate hireDate = CoreDateUtils.parse(
                    crs.getString("hire_date"),
                     "yyyy-MM-dd");
            long salary = crs.getLong("salary");
            long commission = crs.getLong("commission");
            Department department = departmentlist.stream()
                    .filter(dept -> {
                        try {
                            return dept.getDepId().equals(crs.getString("dep_id"));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }).findFirst().get();

            return new Employee(id, name, job, manager, hireDate, salary, commission, department);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
     private static DBParam[] paramList(Employee employee) {
        List<DBParam> paramList = new LinkedList<>();
        paramList.add(new DBParam(Types.VARCHAR, "emp_id", employee.getEmpID()));
        paramList.add(new DBParam(Types.VARCHAR, "emp_name", employee.getName()));
        paramList.add(new DBParam(Types.VARCHAR, "job_name", employee.getJob()));
        paramList.add(new DBParam(Types.VARCHAR, "manager_id", employee.getManager().getEmpID()));
        paramList.add(
                new DBParam(Types.VARCHAR, "hire_date", CoreDateUtils.format(employee.getHireDate(), "yyyy-MM-dd")));
        paramList.add(new DBParam(Types.BIGINT, "salary", employee.getSalary()));
        paramList.add(new DBParam(Types.BIGINT, "commission", employee.getCommission()));
        paramList.add(new DBParam(Types.VARCHAR, "dep_id", employee.getDepartment().getDepId()));
        return paramList.toArray(new DBParam[0]);

    }


    public static List<Employee> getEmployeeList() {
        CachedRowSet crs = DB.select_all(TABLE);
        List<Employee> list = new LinkedList<>();

        try {
             while (crs.next()) {
                Employee employee = data(crs); 
                if (employee != null)
                    list.add(employee);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        list.forEach(employee -> {
            String manager_id = employee.getManager().getEmpID();
            if (!manager_id.isEmpty())
            employee.setManager(
                    list.stream()
                            .filter(e -> e.getEmpID().equals(manager_id))
                            .findFirst().get());
            employee.rebaseline();

        });

        return list;
    }

    public static void insert(Employee employee) {

        DB.insert(TABLE, paramList(employee));
    }
}