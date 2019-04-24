package com.PrototypeServer.spring.dao;

import java.util.List;

import com.PrototypeServer.spring.model.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAOImpl implements AdminDAO {

    private static final Logger logger = LoggerFactory.getLogger(AdminDAOImpl.class); //Output logging for debugging.

    private SessionFactory sessionFactory; //definition of Hibernate. Controls the database with the help of Hibernate.
    // Stops the user having to define SQL statements constantly.

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addAdmin(Admin p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(p); //db operation to save user details to the database with the persist command.
        logger.info("Admin saved successfully, Admin Details="+p);//Logger outputs that user has been saved.
    }

    @Override
    public void updateAdmin(Admin p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(p);
        logger.info("Admin updated successfully, Admin Details="+p);
    }

    @Override
    // List all users
    public List<Admin> listAdmins() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Admin> usersList = session.createQuery("from Admin").list();
        for(Admin p : usersList){
            logger.info("Admin List::"+p);
        }
        return usersList;
    }

    @Override
    // Query - Find a user by specifying an ID.
    public Admin getAdminById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Admin p = (Admin) session.get(Admin.class, new Integer(id));
        logger.info("Admin loaded successfully, Admin details="+p);
        return p;
    }

    @Override
    // Query - Remove a user by specifying an ID
    public void removeAdmin(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Admin p = (Admin) session.load(Admin.class, new Integer(id));
        if(null != p){
            session.delete(p);
        }
        logger.info("Admin deleted successfully, admin details="+p);
    }

    @Override
    // Query - Find a user by specifying a username
    public List<Admin> isExist(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = String.format("from Admin where email = '%s'", email);
        //String hql = "from Admin where username = aaa";
        List<Admin> adminList = session.createQuery(hql).list();
        return adminList;
    }
    // TODO Write delete user by username.
}
