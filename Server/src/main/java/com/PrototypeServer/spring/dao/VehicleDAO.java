package com.PrototypeServer.spring.dao;

import com.PrototypeServer.spring.model.Property;
import com.PrototypeServer.spring.model.Vehicle;

import java.util.List;

public interface VehicleDAO {

    //Below the data objects are defined.
    public void addVehicle(Vehicle c);
    public void updateVehicle(Vehicle c);
    public Vehicle getVehicleById(int id);
    public void removeVehicle(int id);
    public List<Vehicle> getVehiclesByCompanyId(int company_id);
}
