package com.example.andy.prototype.model;

public class Employee {

    String name;
    String position;
    int salary;
    int taxId;

    public Employee(String name, String position, int salary, int taxId) {
        this.name = name;
        this.position = position;
        this.salary = salary;
        this.taxId = taxId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }
}
