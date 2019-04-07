package com.example.andy.prototype2.model;

public class Employee {

    int employee_id;
    String surname;
    String forename;
    String title;
    int salary;
    int tax;

    public Employee(int employee_id, String surname, String forename, String title, int salary, int tax ) {
        this.employee_id = employee_id;
        this.surname = surname;
        this.forename = forename;
        this.title = title;
        this.salary = salary;
        this.tax = tax;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getTax() {
        return tax;
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

}
