package com.PrototypeServer.spring.dao;

import java.util.List;

import com.PrototypeServer.spring.model.Company;
import com.PrototypeServer.spring.model.Property;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class PropertyDAOImpl implements PropertyDAO {

    private static final Logger logger = LoggerFactory.getLogger(PropertyDAOImpl.class); //Output logging for debugging.

    private SessionFactory sessionFactory; //definition of Hibernate. Controls the database with the help of Hibernate.
    // Stops the property having to define SQL statements constantly.

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addProperty(Property p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(p); //db operation to save property details to the database with the persist command.
        logger.info("Property saved successfully, Property Details="+p);//Logger outputs that property has been saved.
    }

    @Override
    public void updateProperty(Property p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(p);
        logger.info("Property updated successfully, Property Details="+p);
    }

    @Override
    // Query - Find a property by specifying an ID.
    public Property getPropertyById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Property p = (Property) session.get(Property.class, new Integer(id));
        logger.info("Property loaded successfully, Property details="+p);
        return p;
    }

    @Override
    // Query - Remove a property by specifying an ID
    public void removeProperty(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Property p = (Property) session.load(Property.class, new Integer(id));
        if(null != p){
            session.delete(p);
        }
        logger.info("Property deleted successfully, property details="+p);
    }
    // TODO Write delete property by propertyname.

    @Override
    // Query - Find companies by specifying an user_id.
    public List<Property> getPropertiesByCompanyId(int company_id) {
        Session session = this.sessionFactory.getCurrentSession();
        String query = "from Property where company_id = " + company_id;
        List<Property> propertyList = session.createQuery(query).list();
        for(Property c : propertyList){
            logger.info("Company List::"+c);
        }
        return propertyList;
    }


    @Override
    // List all properties
    public List<Property> listProperties() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Property> propertyList = session.createQuery("from Property").list();
        for(Property c : propertyList){
            logger.info("Property List::"+c);
        }
        return propertyList;
    }
}
