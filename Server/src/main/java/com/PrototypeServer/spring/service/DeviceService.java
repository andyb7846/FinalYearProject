package com.PrototypeServer.spring.service;

import java.util.List;

import com.PrototypeServer.spring.model.Device;

public interface DeviceService {

    // Defining the methods of the DeviceService
    public void addDevice(Device p);
    public void updateDevice(Device p);
    public Device getDeviceById(int id);
    public void removeDevice(int id);
}