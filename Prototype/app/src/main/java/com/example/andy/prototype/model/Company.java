package com.example.andy.prototype.model;

public class Company {

    String name;
    int employees;
    int properties;
    int vehicles;
    int devices;

    public Company(String name, int employees, int properties, int vehicles, int devices) {
        this.name = name;
        this.employees = employees;
        this.properties = properties;
        this.vehicles = vehicles;
        this.devices = devices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    public int getProperties() {
        return properties;
    }

    public void setProperties(int properties) {
        this.properties = properties;
    }

    public int getVehicles() {
        return vehicles;
    }

    public void setVehicles(int vehicles) {
        this.vehicles = vehicles;
    }

    public int getDevices() {
        return devices;
    }

    public void setDevices(int devices) {
        this.devices = devices;
    }
}
