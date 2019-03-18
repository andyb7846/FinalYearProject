package com.PrototypeServer.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PrototypeServer.spring.dao.VehicleDAO;
import com.PrototypeServer.spring.model.Vehicle;

@Service
public class VehicleServiceImpl implements VehicleService {
    //Call an instance of the vehicleDAO. Encapsulation.
    private VehicleDAO vehicleDAO;

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    @Transactional
    public void addVehicle(Vehicle p) {
        this.vehicleDAO.addVehicle(p);
    }

    @Override
    @Transactional
    public void updateVehicle(Vehicle p) {
        this.vehicleDAO.updateVehicle(p);
    }

    @Override
    @Transactional
    public Vehicle getVehicleById(int id) {
        return this.vehicleDAO.getVehicleById(id);
    }

    @Override
    @Transactional
    public void removeVehicle(int id) {
        this.vehicleDAO.removeVehicle(id);
    }
}
