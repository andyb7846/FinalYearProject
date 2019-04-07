package com.PrototypeServer.spring.model;

import javax.persistence.*;

@Entity //Define entity in Hibernate
@Table(name="VEHICLE") //Define corresponding table for Company.

public class Vehicle {

    @Id
    @Column(name="vehicle_id") //Unique Identifier; Primary Key.
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Value doesn't need to be provided. It'll increment by itself.
    private int vehicle_id;

    private String manufacturer;

    private String model;

    private String registration;

    private int yearly_cost;

    private String create_time;

    private String update_time;

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    public Vehicle(){}

    public Vehicle(Company company, String manufacturer, String model, String registration, int yearly_cost, String create_time) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.registration = registration;
        this.yearly_cost = yearly_cost;
        this.create_time = create_time;
        this.company = company;
    }

    public int getYearly_cost() {
        return yearly_cost;
    }

    public void setYearly_cost(int yearly_cost) {
        this.yearly_cost = yearly_cost;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
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
