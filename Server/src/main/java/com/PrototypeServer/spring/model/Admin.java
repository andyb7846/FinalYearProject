package com.PrototypeServer.spring.model;

import javax.persistence.*;
import java.util.Collection;

// User class is responsible for the user table in the MySQL Database.

@Entity //Define entity in Hibernate
@Table(name="ADMIN") //Define corresponding table for user.

// This class is essential for Hibernate interaction.
// This makes Hibernate understand what I want to do with the users.

public class Admin {

    @Id
    @Column(name="admin_id") //Unique Identifier; Primary Key.
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Value doesn't need to be provided. It'll increment by itself.
    private int admin_id;

    private String name;

    private String password;

    private String email;

    private String create_time;

    private String update_time;

    public Admin(){} // KEEP FOR EMPTY USER TESTING

    public Admin(String name, String password, String email, String create_time){
        this.name = name;
        this.password = password;
        this.email = email;
        this.create_time = create_time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public String toString(){
        return "id="+admin_id+", name="+name+", password="+password;
    }
}
