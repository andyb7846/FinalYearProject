package com.PrototypeServer.spring.dao;

import com.PrototypeServer.spring.model.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

    private static final Logger logger = LoggerFactory.getLogger(CompanyDAOImpl.class); //Output logging for debugging.

    private SessionFactory sessionFactory; //definition of Hibernate. Controls the database with the help of Hibernate.
    // Stops the user having to define SQL statements constantly.

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addCompany(Company c) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(c); //db operation to save user details to the database with the persist command.
        logger.info("Company saved successfully, Company Details="+c);//Logger outputs that user has been saved.
    }

    @Override
    public void updateCompany(Company c) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(c);
        logger.info("Company updated successfully, Company Details="+c);
    }

    @Override
    // List all companies
    public List<Company> listCompanies() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Company> companyList = session.createQuery("from Company").list();
        for(Company c : companyList){
            logger.info("Company List::"+c);
        }
        return companyList;
    }

    @Override
    // Query - Find a company by specifying an ID.
    public Company getCompanyById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Company p = (Company) session.load(Company.class, new Integer(id));
        logger.info("Company loaded successfully, Company details="+p);
        return p;
    }

    @Override
    // Query - Find companies by specifying an user_id.
    public List<Company> getCompaniesByUserId(int user_id) {
        Session session = this.sessionFactory.getCurrentSession();
        String query = "from Company where user_id = " + user_id;
        List<Company> companyList = session.createQuery(query).list();
        for(Company c : companyList){
            logger.info("Company List::"+c);
        }
        return companyList;
    }

    @Override
    // Query - Remove a company by specifying an ID
    public void removeCompany(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Company c = (Company) session.load(Company.class, new Integer(id));
        if(null != c){
            session.delete(c);
        }
        logger.info("Company deleted successfully, company details="+c);
    }

}
