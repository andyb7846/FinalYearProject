package com.example.andy.prototype2.model;

public class Company {

    int company_id;
    String name;
    int employees;
    int properties;
    int vehicles;
    int devices;
    int gross;
    int cost;
    int income;

    public Company(int company_id, String name, int employees, int properties, int devices, int vehicles, int gross, int cost) {
        this.company_id = company_id;
        this.name = name;
        this.employees = employees;
        this.properties = properties;
        this.vehicles = vehicles;
        this.devices = devices;
        this.gross = gross;
        this.cost = cost;
        this.income = gross - cost;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
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

    public int getGross() {
        return gross;
    }

    public void setGross(int gross) {
        this.gross = gross;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }
}
