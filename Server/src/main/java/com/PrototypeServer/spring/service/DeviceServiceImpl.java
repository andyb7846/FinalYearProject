package com.PrototypeServer.spring.service;

import java.util.List;

import com.PrototypeServer.spring.model.Property;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PrototypeServer.spring.dao.DeviceDAO;
import com.PrototypeServer.spring.model.Device;

@Service
public class DeviceServiceImpl implements DeviceService {
    //Call an instance of the deviceDAO. Encapsulation.
    private DeviceDAO deviceDAO;

    public void setDeviceDAO(DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    @Override
    @Transactional
    public void addDevice(Device p) {
        this.deviceDAO.addDevice(p);
    }

    @Override
    @Transactional
    public void updateDevice(Device p) {
        this.deviceDAO.updateDevice(p);
    }

    @Override
    @Transactional
    public Device getDeviceById(int id) {
        return this.deviceDAO.getDeviceById(id);
    }

    @Override
    @Transactional
    public void removeDevice(int id) {
        this.deviceDAO.removeDevice(id);
    }
    
    @Override
    @Transactional
    public List<Device> getDevicesByCompanyId(int company_id){
        return deviceDAO.getDevicesByCompanyId(company_id);
    }
}
