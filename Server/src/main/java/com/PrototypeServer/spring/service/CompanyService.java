package com.PrototypeServer.spring.service;

import com.PrototypeServer.spring.model.Company;

import java.util.List;

public interface CompanyService {

    // Defining the methods of the UserService
    public void addCompany(Company c);
    public void updateCompany(Company c);
    public List<Company> listCompanies();
    public List<Company> getCompaniesByUserId(int user_id);
    public void removeCompany(int id);
}
