package com.PrototypeServer.spring.dao;

import java.util.List;

import com.PrototypeServer.spring.model.Admin;

public interface AdminDAO { //DAO means Data Objects

    //Below the data objects are defined.
    public void addAdmin(Admin p);
    public void updateAdmin(Admin p);
    public List<Admin> listAdmins();
    public Admin getAdminById(int id);
    public void removeAdmin(int id);
    public List<Admin> isExist(String email);
}
