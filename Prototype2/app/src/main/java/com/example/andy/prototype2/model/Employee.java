package com.example.andy.prototype2.model;

public class Employee {

    int employee_id;
    String surname;
    String forename;
    String title;
    int salary;
    int tax;
    String tax_id;
    String gov_tax_code;

    public Employee(int employee_id, String surname, String forename, String title, int salary, int tax, String tax_id, String gov_tax_code) {
        this.employee_id = employee_id;
        this.surname = surname;
        this.forename = forename;
        this.title = title;
        this.salary = salary;
        this.tax = tax;
        this.tax_id = tax_id;
        this.gov_tax_code = gov_tax_code;
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

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getGov_tax_code() {
        return gov_tax_code;
    }

    public void setGov_tax_code(String gov_tax_code) {
        this.gov_tax_code = gov_tax_code;
    }
}
