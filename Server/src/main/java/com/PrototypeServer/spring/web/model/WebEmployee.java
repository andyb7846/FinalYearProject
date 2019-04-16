package com.PrototypeServer.spring.web.model;

import com.PrototypeServer.spring.model.Company;

import javax.persistence.*;
import java.util.Collection;


public class WebEmployee {

    private int employee_id;

    private Company company;

    private String forename;

    private String surname;

    private String job_name;

    private int salary;

    private int tax;

    private String create_time;

    private String update_time;

    public WebEmployee(){}

    public WebEmployee(Company company, String forename, String surname, String job_name,  int salary, int tax, String create_time) {
        this.company = company;
        this.forename = forename;
        this.surname = surname;
        this.job_name = job_name;
        this.salary = salary;
        this.tax = tax;
        this.create_time = create_time;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
