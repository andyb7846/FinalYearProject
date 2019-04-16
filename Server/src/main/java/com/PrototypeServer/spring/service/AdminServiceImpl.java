package com.PrototypeServer.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PrototypeServer.spring.dao.AdminDAO;
import com.PrototypeServer.spring.model.Admin;

@Service
public class AdminServiceImpl implements AdminService {
    //Call an instance of the adminDAO. Encapsulation.
    private AdminDAO adminDAO;

    public void setAdminDAO(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Override
    @Transactional
    public void addAdmin(Admin p) {
        this.adminDAO.addAdmin(p);
    }

    @Override
    @Transactional
    public void updateAdmin(Admin p) {
        this.adminDAO.updateAdmin(p);
    }

    @Override
    @Transactional
    public List<Admin> listAdmins() {
        return this.adminDAO.listAdmins();
    }

    @Override
    @Transactional
    public Admin getAdminById(int id) {
        return this.adminDAO.getAdminById(id);
    }

    @Override
    @Transactional
    public void removeAdmin(int id) {
        this.adminDAO.removeAdmin(id);
    }

    @Override
    @Transactional
    public List<Admin> isExist(String email) {
        return this.adminDAO.isExist(email);
    }
}
