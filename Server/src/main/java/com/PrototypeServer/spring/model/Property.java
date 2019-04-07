package com.PrototypeServer.spring.model;

import javax.persistence.*;

@Entity //Define entity in Hibernate
@Table(name="PROPERTY") //Define corresponding table for Company.

public class Property {

    @Id
    @Column(name="property_id") //Unique Identifier; Primary Key.
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Value doesn't need to be provided. It'll increment by itself.
    private int property_id;

    private String street_name;

    private String house_number;

    private String post_code;

    private int yearly_cost;

    private String create_time;

    private String update_time;

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    public Property(){}

    public Property(Company company, String street_name, String house_number, String post_code, int yearly_cost, String create_time) {
        this.street_name = street_name;
        this.house_number = house_number;
        this.post_code = post_code;
        this.yearly_cost = yearly_cost;
        this.create_time = create_time;
        this.company = company;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public int getYearly_cost() {
        return yearly_cost;
    }

    public void setYearly_cost(int yearly_cost) {
        this.yearly_cost = yearly_cost;
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
