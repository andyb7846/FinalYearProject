package com.PrototypeServer.spring.dao;

import java.util.List;

import com.PrototypeServer.spring.model.Company;
import com.PrototypeServer.spring.model.Property;
import com.PrototypeServer.spring.model.Vehicle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDAOImpl implements VehicleDAO {

    private static final Logger logger = LoggerFactory.getLogger(VehicleDAOImpl.class); //Output logging for debugging.

    private SessionFactory sessionFactory; //definition of Hibernate. Controls the database with the help of Hibernate.
    // Stops the vehicle having to define SQL statements constantly.

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addVehicle(Vehicle p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(p); //db operation to save vehicle details to the database with the persist command.
        logger.info("Vehicle saved successfully, Vehicle Details="+p);//Logger outputs that vehicle has been saved.
    }

    @Override
    public void updateVehicle(Vehicle p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(p);
        logger.info("Vehicle updated successfully, Vehicle Details="+p);
    }

    @Override
    // Query - Find a vehicle by specifying an ID.
    public Vehicle getVehicleById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Vehicle p = (Vehicle) session.load(Vehicle.class, new Integer(id));
        logger.info("Vehicle loaded successfully, Vehicle details="+p);
        return p;
    }

    @Override
    // Query - Remove a vehicle by specifying an ID
    public void removeVehicle(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Vehicle p = (Vehicle) session.load(Vehicle.class, new Integer(id));
        if(null != p){
            session.delete(p);
        }
        logger.info("Vehicle deleted successfully, vehicle details="+p);
    }
    // TODO Write delete vehicle by vehiclename.

    @Override
    // Query - Find companies by specifying an user_id.
    public List<Vehicle> getVehiclesByCompanyId(int company_id) {
        Session session = this.sessionFactory.getCurrentSession();
        String query = "from Vehicle where company_id = " + company_id;
        List<Vehicle> vehicleList = session.createQuery(query).list();
        for(Vehicle c : vehicleList){
            logger.info("Company List::"+c);
        }
        return vehicleList;
    }


    @Override
    // List all vehicles
    public List<Vehicle> listVehicles() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Vehicle> vehicleList = session.createQuery("from Vehicle").list();
        for(Vehicle c : vehicleList){
            logger.info("Vehicle List::"+c);
        }
        return vehicleList;
    }
}
