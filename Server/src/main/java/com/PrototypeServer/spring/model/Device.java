package com.PrototypeServer.spring.model;

import javax.persistence.*;

@Entity //Define entity in Hibernate
@Table(name="DEVICE") //Define corresponding table for Company.

public class Device {

    @Id
    @Column(name="device_id") //Unique Identifier; Primary Key.
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Value doesn't need to be provided. It'll increment by itself.
    private int device_id;

    private String name;

    private String create_time;

    private String update_time;

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    private Company company;

    public Device(){}

    public Device(Company company, String name, String create_time){

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

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
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
