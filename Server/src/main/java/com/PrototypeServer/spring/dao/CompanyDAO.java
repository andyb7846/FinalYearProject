package com.PrototypeServer.spring.dao;

import com.PrototypeServer.spring.model.Company;

import java.util.List;

public interface CompanyDAO {

    //Below the data objects are defined.
    public void addCompany(Company c);
    public void updateCompany(Company c);
    public Company getCompanyById(int id);
    public List<Company> listCompanies();
    public List<Company> getCompaniesByUserId(int user_id);
    public void removeCompany(int id);
}
