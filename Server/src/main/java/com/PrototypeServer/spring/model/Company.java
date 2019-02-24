package com.PrototypeServer.spring.model;

import javax.persistence.*;

@Entity //Define entity in Hibernate
@Table(name="COMPANY") //Define corresponding table for Company.

public class Company {

    @Id
    @Column(name="company_id") //Unique Identifier; Primary Key.
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Value doesn't need to be provided. It'll increment by itself.
    private int company_id;

    //private String user_id;

    private String name;

    private String create_time;

    private String update_time;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    public Company(){}

    public Company(String name, String create_time, User user){
        this.name = name;
        this.create_time = create_time;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString(){
        return "id="+this.company_id+", name="+this.name;
    }
}
