package com.example.andy.prototype2.model;

public class Vehicle {

    int vehicle_id;
    String manufacturer;
    String model;
    String registration;
    int yearly_cost;

    public Vehicle(int vehicle_id, String manufacturer, String model, String registration, int yearly_cost) {
        this.vehicle_id = vehicle_id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.registration = registration;
        this.yearly_cost = yearly_cost;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
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

    public int getYearly_cost() {
        return yearly_cost;
    }

    public void setYearly_cost(int yearly_cost) {
        this.yearly_cost = yearly_cost;
    }
}
