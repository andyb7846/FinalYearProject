package com.PrototypeServer.spring.service;

import java.util.List;

import com.PrototypeServer.spring.model.Vehicle;

public interface VehicleService {

    // Defining the methods of the VehicleService
    public void addVehicle(Vehicle p);
    public void updateVehicle(Vehicle p);
    public Vehicle getVehicleById(int id);
    public void removeVehicle(int id);
    public List<Vehicle> listVehicles();
    public List<Vehicle> getVehiclesByCompanyId(int company_id);
}