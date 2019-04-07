package com.PrototypeServer.spring.service;


import com.PrototypeServer.spring.model.Employee;

import java.util.List;

public interface EmployeeService {

    // Defining the methods of the EmployeeService
    public void addEmployee(Employee p);
    public void updateEmployee(Employee p);
    public Employee getEmployeeById(int id);
    public void removeEmployee(int id);
    public List<Employee> getEmployeesByCompanyId(int company_id);
}
