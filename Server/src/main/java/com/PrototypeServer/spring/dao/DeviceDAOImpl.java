package com.PrototypeServer.spring.dao;

import java.util.List;

import com.PrototypeServer.spring.model.Device;
import com.PrototypeServer.spring.model.Property;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class DeviceDAOImpl implements DeviceDAO {

    private static final Logger logger = LoggerFactory.getLogger(DeviceDAOImpl.class); //Output logging for debugging.

    private SessionFactory sessionFactory; //definition of Hibernate. Controls the database with the help of Hibernate.
    // Stops the device having to define SQL statements constantly.

    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addDevice(Device p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(p); //db operation to save device details to the database with the persist command.
        logger.info("Device saved successfully, Device Details="+p);//Logger outputs that device has been saved.
    }

    @Override
    public void updateDevice(Device p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(p);
        logger.info("Device updated successfully, Device Details="+p);
    }

    @Override
    // Query - Find a device by specifying an ID.
    public Device getDeviceById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Device p = (Device) session.get(Device.class, new Integer(id));
        logger.info("Device loaded successfully, Device details="+p);
        return p;
    }

    @Override
    // Query - Remove a device by specifying an ID
    public void removeDevice(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Device p = (Device) session.load(Device.class, new Integer(id));
        if(null != p){
            session.delete(p);
        }
        logger.info("Device deleted successfully, device details="+p);
    }
    // TODO Write delete device by devicename.

    @Override
    // Query - Find companies by specifying an user_id.
    public List<Device> getDevicesByCompanyId(int company_id) {
        Session session = this.sessionFactory.getCurrentSession();
        String query = "from Device where company_id = " + company_id;
        List<Device> deviceList = session.createQuery(query).list();
        for(Device c : deviceList){
            logger.info("Company List::"+c);
        }
        return deviceList;
    }


    @Override
    // List all devices
    public List<Device> listDevices() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Device> deviceList = session.createQuery("from Device").list();
        for(Device c : deviceList){
            logger.info("Device List::"+c);
        }
        return deviceList;
    }
}
