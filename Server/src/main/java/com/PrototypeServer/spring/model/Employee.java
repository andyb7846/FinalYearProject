package com.PrototypeServer.spring.model;

import javax.persistence.*;
import java.util.Collection;

@Entity //Define entity in Hibernate
@Table(name="EMPLOYEE") //Define corresponding table for Company.

public class Employee {

    @Id
    @Column(name="employee_id") //Unique Identifier; Primary Key.
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Value doesn't need to be provided. It'll increment by itself.
    private int employee_id;

    @OneToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    private String forename;

    private String surname;

    private String job_name;

    private String tax_id;

    private String goveronment_tax_code;

    private int salary;

    private int tax;

    private String create_time;

    private String update_time;

    public Employee(User user, Company company, String forename, String surname, String job_name, String tax_id, String goveronment_tax_code, int salary, int tax, String create_time) {
        this.user = user;
        this.company = company;
        this.forename = forename;
        this.surname = surname;
        this.job_name = job_name;
        this.tax_id = tax_id;
        this.goveronment_tax_code = goveronment_tax_code;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
