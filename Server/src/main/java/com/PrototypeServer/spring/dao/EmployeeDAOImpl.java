package com.PrototypeServer.spring.dao;

import java.util.List;

import com.PrototypeServer.spring.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class); //Output logging for debugging.

    private SessionFactory sessionFactory; //definition of Hibernate. Controls the database with the help of Hibernate.
    // Stops the employee having to define SQL statements constantly.

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addEmployee(Employee p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(p); //db operation to save employee details to the database with the persist command.
        logger.info("Employee saved successfully, Employee Details="+p);//Logger outputs that employee has been saved.
    }

    @Override
    public void updateEmployee(Employee p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(p);
        logger.info("Employee updated successfully, Employee Details="+p);
    }

    @Override
    // Query - Find a employee by specifying an ID.
    public Employee getEmployeeById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Employee p = (Employee) session.load(Employee.class, new Integer(id));
        logger.info("Employee loaded successfully, Employee details="+p);
        return p;
    }

    @Override
    // Query - Remove a employee by specifying an ID
    public void removeEmployee(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Employee p = (Employee) session.load(Employee.class, new Integer(id));
        if(null != p){
            session.delete(p);
        }
        logger.info("Employee deleted successfully, employee details="+p);
    }
    // TODO Write delete employee by employeename.
}
