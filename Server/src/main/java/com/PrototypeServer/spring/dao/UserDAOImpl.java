package com.PrototypeServer.spring.dao;

import java.util.List;

import com.PrototypeServer.spring.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class); //Output logging for debugging.

    private SessionFactory sessionFactory; //definition of Hibernate. Controls the database with the help of Hibernate.
    // Stops the user having to define SQL statements constantly.

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addUser(User p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(p); //db operation to save user details to the database with the persist command.
        logger.info("User saved successfully, User Details="+p);//Logger outputs that user has been saved.
    }

    @Override
    public void updateUser(User p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(p);
        logger.info("User updated successfully, User Details="+p);
    }

    @Override
    // List all users
    public List<User> listUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> usersList = session.createQuery("from User").list();
        for(User p : usersList){
            logger.info("User List::"+p);
        }
        return usersList;
    }

    @Override
    // Query - Find a user by specifying an ID.
    public User getUserById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User p = (User) session.load(User.class, new Integer(id));
        logger.info("User loaded successfully, User details="+p);
        return p;
    }

    @Override
    // Query - Find a user by specifying an ID.
    public User getUserByUniqueId(String uniqueId) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = String.format("from User where unique_id = '%s'", uniqueId);
        List<User> userList = session.createQuery(hql).list();

        if(userList.isEmpty()){
            logger.info("No user found");
            return null;
        }

        logger.info("User loaded successfully, User details="+userList.get(0));
        return userList.get(0);

    }

    @Override
    // Query - Remove a user by specifying an ID
    public void removeUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User p = (User) session.load(User.class, new Integer(id));
        if(null != p){
            session.delete(p);
        }
        logger.info("User deleted successfully, user details="+p);
    }

    @Override
    // Query - Find a user by specifying a username
    public List<User> isExist(String email) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = String.format("from User where email = '%s'", email);
        //String hql = "from User where username = aaa";
        List<User> userList = session.createQuery(hql).list();
        return userList;
    }
    // TODO Write delete user by username.
}
