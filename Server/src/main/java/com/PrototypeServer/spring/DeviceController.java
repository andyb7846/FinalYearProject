package com.PrototypeServer.spring;

import com.PrototypeServer.spring.model.*;
import com.PrototypeServer.spring.service.CompanyService;
import com.PrototypeServer.spring.service.DeviceService;
import com.PrototypeServer.spring.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@RestController
public class DeviceController {

    private DeviceService deviceService;
    private CompanyService companyService;
    private UserService userService;

    @Autowired(required=true)
    @Qualifier(value="deviceService")
    public void setDeviceService(DeviceService ps){
        this.deviceService = ps;
    }

    @Autowired(required=true)
    @Qualifier(value="companyService")
    public void setCompanyService(CompanyService ps){
        this.companyService = ps;
    }

    @Autowired(required=true)
    @Qualifier(value="userService")
    public void setUserService(UserService ps){
        this.userService = ps;
    }

    //CREATE
    @RequestMapping(value = "/device/create", method = RequestMethod.POST)
    public Object create(@RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="brand") String brand,
                         @RequestParam(value="model") String model,
                         @RequestParam(value="yearly_cost") int yearlyCost,
                         Model model2) {

        User user = this.userService.getUserByUniqueId(uniqueId);
        if (user == null) {
            return new ErrorResponse(3, "No user found with this unique_id");
        }

        Company company = null;
        for(Iterator iterator = user.getCompanies().iterator(); iterator.hasNext();) {
            Company comp = (Company) iterator.next();
            if (companyId == comp.getCompany_id()) {
                company = comp;
                break;
            }
        }

        if(company == null){
            return new ErrorResponse(3, "No company found with this unique_id");
        }

        //create new device
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Device device = new Device(company, brand, model, yearlyCost, dateFormat.format(new Date()));
        this.deviceService.addDevice(device);
        return new SuccessResponse(0, user);
    }

    //DELETE
    @RequestMapping(value = "/device/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="device_id") int deviceId,
                         Model model){

        Device device = this.deviceService.getDeviceById(deviceId);
        if(device == null){
            return new ErrorResponse(3, "No device found with this id");
        }
        if(companyId != device.getCompany().getCompany_id()){
            return new ErrorResponse(3, "No device found with this company id");
        }

        if(uniqueId != device.getCompany().getUser().getUnique_id()){
            return new ErrorResponse(3, "No device found with this unique id");
        }

        this.deviceService.removeDevice(deviceId);
        return new SuccessResponse(0, new User());
    }

    //UPDATE
    @RequestMapping(value = "/device/update", method = RequestMethod.POST)
    public Object create(@RequestParam(value="device_id") int deviceId,
                         @RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="brand") String brand,
                         @RequestParam(value="model") String model,
                         @RequestParam(value="yearly_cost") int yearlyCost,
                         Model model2) {

        User user = this.userService.getUserByUniqueId(uniqueId);
        if (user == null) {
            return new ErrorResponse(3, "No user found with this unique_id");
        }

        Company company = null;
        for(Iterator iterator = user.getCompanies().iterator(); iterator.hasNext();) {
            Company comp = (Company) iterator.next();
            if (companyId == comp.getCompany_id()) {
                company = comp;
                break;
            }
        }

        if(company == null){
            return new ErrorResponse(3, "No company found with this unique_id");
        }

        //create new device
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Device device = new Device(company, brand, model, yearlyCost, dateFormat.format(new Date()));
        device.setDevice_id(deviceId);
        this.deviceService.updateDevice(device);
        return new SuccessResponse(0, user);
    }

    @RequestMapping(value = "/device/require", method = RequestMethod.POST)
    public Object require(@RequestParam(value="unique_id") String uniqueId, @RequestParam(value="company_id") int companyId, Model model) {

        if(uniqueId != null) {
            User user = this.userService.getUserByUniqueId(uniqueId);
            if (user == null) {
                return new ErrorResponse(3, "No user found with this unique_id");
            } else {
                for(Object company : user.getCompanies()){
                    if(((Company)company).getCompany_id() == companyId){
                        //return new ErrorResponse(4, "actually succeeded");
                        JSONArray ja = new JSONArray();
                        for(Device device : ((Company) company).getDevices()){
                            JSONObject tmp = new JSONObject();
                            tmp.put("device_id", device.getDevice_id());
                            tmp.put("brand", device.getBrand());
                            tmp.put("model", device.getModel());
                            tmp.put("yearly_cost", device.getYearly_cost());
                            ja.put(tmp);

                        }
                        return ja.toString();
                    }
                }
                return new ErrorResponse(2, "No company found with this company_id and unique_id"); //Verification Statement
            }
        }
        else {
            return new ErrorResponse(2, "company id or unique id should not be empty"); //Verification Statement
        }
    }
}
