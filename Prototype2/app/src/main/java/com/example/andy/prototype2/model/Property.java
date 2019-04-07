package com.example.andy.prototype2.model;

public class Property {

    int property_id;
    String street_name;
    String house_number;
    String post_code;
    int yearly_cost;

    public Property(int property_id, String street_name, String house_number, String post_code, int yearly_cost) {
        this.property_id = property_id;
        this.street_name = street_name;
        this.house_number = house_number;
        this.post_code = post_code;
        this.yearly_cost = yearly_cost;
    }

    public int getProperty_id() {
        return property_id;
    }

    public void setProperty_id(int property_id) {
        this.property_id = property_id;
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
}
