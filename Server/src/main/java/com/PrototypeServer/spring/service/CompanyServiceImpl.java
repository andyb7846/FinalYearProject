package com.PrototypeServer.spring.service;

import com.PrototypeServer.spring.dao.CompanyDAO;
import com.PrototypeServer.spring.model.Company;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService{

    private CompanyDAO companyDAO;

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Transactional
    @Override
    public void addCompany(Company c) {
        this.companyDAO.addCompany(c);
    }

    @Transactional
    @Override
    public void updateCompany(Company c) {
        this.companyDAO.updateCompany(c);
    }

    @Transactional
    @Override
    public List<Company> listCompanies() {
        return companyDAO.listCompanies();
    }

    @Transactional
    @Override
    public List<Company> getCompaniesByUserId(int user_id) {
        return companyDAO.getCompaniesByUserId(user_id);
    }

    @Transactional
    @Override
    public void removeCompany(int id) {
        this.companyDAO.removeCompany(id);
    }
}
