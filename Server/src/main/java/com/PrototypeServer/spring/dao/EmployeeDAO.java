package com.PrototypeServer.spring.dao;

import java.util.List;

import com.PrototypeServer.spring.model.Device;
import com.PrototypeServer.spring.model.Employee;
import com.PrototypeServer.spring.model.User;

public interface EmployeeDAO { //DAO means Data Objects

    //Below the data objects are defined.
    public void addEmployee(Employee p);
    public void updateEmployee(Employee p);
    public List<Employee> listEmployees();
    public Employee getEmployeeById(int id);
    public void removeEmployee(int id);
    public List<Employee> getEmployeesByCompanyId(int company_id);
}
