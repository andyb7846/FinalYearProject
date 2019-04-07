package com.PrototypeServer.spring.dao;

import com.PrototypeServer.spring.model.Company;
import com.PrototypeServer.spring.model.Property;

import java.util.List;

public interface PropertyDAO {

    //Below the data objects are defined.
    public void addProperty(Property c);
    public void updateProperty(Property c);
    public Property getPropertyById(int id);
    public void removeProperty(int id);
    public List<Property> getPropertiesByCompanyId(int company_id);
}
