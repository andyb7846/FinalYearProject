package com.PrototypeServer.spring.dao;

import java.util.List;

import com.PrototypeServer.spring.model.User;

public interface UserDAO { //DAO means Data Objects

    //Below the data objects are defined.
    public void addUser(User p);
    public void updateUser(User p);
    public List<User> listUsers();
    public User getUserById(int id);
    public User getUserByUniqueId(String uniqueId);
    public void removeUser(int id);
    public List<User> isExist(String username);
}
