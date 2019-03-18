package com.PrototypeServer.spring.dao;

import com.PrototypeServer.spring.model.Device;

import java.util.List;

public interface DeviceDAO {

    //Below the data objects are defined.
    public void addDevice(Device c);
    public void updateDevice(Device c);
    public Device getDeviceById(int id);
    public void removeDevice(int id);
}