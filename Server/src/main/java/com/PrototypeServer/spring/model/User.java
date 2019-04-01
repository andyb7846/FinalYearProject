package com.PrototypeServer.spring.model;

import javax.persistence.*;
import java.util.Collection;

// User class is responsible for the user table in the MySQL Database.

@Entity //Define entity in Hibernate
@Table(name="USER") //Define corresponding table for user.

// This class is essential for Hibernate interaction.
// This makes Hibernate understand what I want to do with the users.

public class User {

    @Id
    @Column(name="user_id") //Unique Identifier; Primary Key.
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Value doesn't need to be provided. It'll increment by itself.
    private int user_id;

    private String unique_id;

    private String username;

    private String password;

    private String create_time;

    private String update_time;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Collection<Company> companies;

    public User(){} // KEEP FOR EMPTY USER TESTING

    public User(String username, String password, String unique_id, String create_time){
        this.username = username;
        this.password = password;
        this.unique_id = unique_id;
        this.create_time = create_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
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

    public Collection getCompanies() {
        return companies;
    }

    public void setCompanies(Collection companies) {
        this.companies = companies;
    }

    @Override
    public String toString(){
        return "id="+user_id+", username="+username+", password="+password;
    }
}
