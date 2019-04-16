package com.PrototypeServer.spring.dao;

import com.PrototypeServer.spring.model.Device;
import com.PrototypeServer.spring.model.Property;

import java.util.List;

public interface DeviceDAO {

    //Below the data objects are defined.
    public void addDevice(Device c);
    public void updateDevice(Device c);
    public Device getDeviceById(int id);
    public List<Device> listDevices();
    public void removeDevice(int id);
    public List<Device> getDevicesByCompanyId(int company_id);
}