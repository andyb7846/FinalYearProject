package com.PrototypeServer.spring.model;

import javax.persistence.*;

@Entity //Define entity in Hibernate
@Table(name="VEHICLE") //Define corresponding table for Company.

public class Vehicle {

    @Id
    @Column(name="vehicle_id") //Unique Identifier; Primary Key.
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Value doesn't need to be provided. It'll increment by itself.
    private int vehicle_id;

    private String name;

    private String create_time;

    private String update_time;

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    public Vehicle(){}

    public Vehicle(Company company, String name, String create_time){

        this.create_time = create_time;
        this.company = company;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
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
