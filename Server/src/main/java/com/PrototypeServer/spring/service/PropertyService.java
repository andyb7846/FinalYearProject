package com.PrototypeServer.spring.service;

import java.util.List;

import com.PrototypeServer.spring.model.Property;

public interface PropertyService {

    // Defining the methods of the PropertyService
    public void addProperty(Property p);
    public void updateProperty(Property p);
    public Property getPropertyById(int id);
    public List<Property> listProperties();
    public void removeProperty(int id);
    public List<Property> getPropertiesByCompanyId(int company_id);
}