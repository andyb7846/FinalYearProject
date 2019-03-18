package com.PrototypeServer.spring.service;


import com.PrototypeServer.spring.model.Employee;

public interface EmployeeService {

    // Defining the methods of the EmployeeService
    public void addEmployee(Employee p);
    public void updateEmployee(Employee p);
    public Employee getEmployeeById(int id);
    public void removeEmployee(int id);
}
