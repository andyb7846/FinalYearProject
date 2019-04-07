package com.example.andy.prototype2.model;

public class Device {

    int device_id;
    String brand;
    String model;
    int yearly_cost;

    public Device(int device_id, String brand, String model, int yearly_cost) {
        this.device_id = device_id;
        this.brand = brand;
        this.model = model;
        this.yearly_cost = yearly_cost;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearly_cost() {
        return yearly_cost;
    }

    public void setYearly_cost(int yearly_cost) {
        this.yearly_cost = yearly_cost;
    }
}
