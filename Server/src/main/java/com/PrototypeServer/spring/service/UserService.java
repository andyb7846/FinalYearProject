package com.PrototypeServer.spring.service;

import java.util.List;

import com.PrototypeServer.spring.model.User;

public interface UserService {

    // Defining the methods of the UserService
    public void addUser(User p);
    public void updateUser(User p);
    public List<User> listUsers();
    public User getUserById(int id);
    public User getUserByUniqueId(String uniqueId);
    public void removeUser(int id);
    public List<User> isExist(String email);
}
