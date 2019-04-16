package com.PrototypeServer.spring.service;

import java.util.List;

import com.PrototypeServer.spring.model.Company;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PrototypeServer.spring.dao.PropertyDAO;
import com.PrototypeServer.spring.model.Property;

@Service
public class PropertyServiceImpl implements PropertyService {
    //Call an instance of the propertyDAO. Encapsulation.
    private PropertyDAO propertyDAO;

    public void setPropertyDAO(PropertyDAO propertyDAO) {
        this.propertyDAO = propertyDAO;
    }

    @Override
    @Transactional
    public void addProperty(Property p) {
        this.propertyDAO.addProperty(p);
    }

    @Override
    @Transactional
    public void updateProperty(Property p) {
        this.propertyDAO.updateProperty(p);
    }

    @Override
    @Transactional
    public Property getPropertyById(int id) {
        return this.propertyDAO.getPropertyById(id);
    }

    @Transactional
    @Override
    public List<Property> listProperties() {
        return propertyDAO.listProperties();
    }

    @Override
    @Transactional
    public void removeProperty(int id) {
        this.propertyDAO.removeProperty(id);
    }

    @Override
    @Transactional
    public List<Property> getPropertiesByCompanyId(int company_id){
        return propertyDAO.getPropertiesByCompanyId(company_id);
    }
}
