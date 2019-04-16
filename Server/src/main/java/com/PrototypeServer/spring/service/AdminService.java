package com.PrototypeServer.spring.service;

import java.util.List;

import com.PrototypeServer.spring.model.Admin;

public interface AdminService {

    // Defining the methods of the AdminService
    public void addAdmin(Admin p);
    public void updateAdmin(Admin p);
    public List<Admin> listAdmins();
    public Admin getAdminById(int id);
    public void removeAdmin(int id);
    public List<Admin> isExist(String email);
}
