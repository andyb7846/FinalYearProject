package com.example.andy.prototype2.model;

public class Employee {

    String surname;
    String forename;
    String title;
    int salary;
    int tax;
    String tax_id;
    String goveronment_tax_code;

    public Employee(String surname, String forename, String title, int salary, int tax, String tax_id, String goveronment_tax_code) {
        this.surname = surname;
        this.forename = forename;
        this.title = title;
        this.salary = salary;
        this.tax = tax;
        this.tax_id = tax_id;
        this.goveronment_tax_code = goveronment_tax_code;
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

    public String getGoveronment_tax_code() {
        return goveronment_tax_code;
    }

    public void setGoveronment_tax_code(String goveronment_tax_code) {
        this.goveronment_tax_code = goveronment_tax_code;
    }
}
