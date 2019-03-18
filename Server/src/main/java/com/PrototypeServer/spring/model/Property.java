package com.PrototypeServer.spring.model;

import javax.persistence.*;

@Entity //Define entity in Hibernate
@Table(name="PROPERTY") //Define corresponding table for Company.

public class Property {

    @Id
    @Column(name="property_id") //Unique Identifier; Primary Key.
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Value doesn't need to be provided. It'll increment by itself.
    private int property_id;

    private String name;

    private String create_time;

    private String update_time;

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    public Property(){}

    public Property(Company company, String name, String create_time){

        this.name = name;
        this.create_time = create_time;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProperty_id() {
        return property_id;
    }

    public void setProperty_id(int property_id) {
        this.property_id = property_id;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
