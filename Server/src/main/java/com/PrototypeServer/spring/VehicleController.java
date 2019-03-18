package com.PrototypeServer.spring;

import com.PrototypeServer.spring.model.*;
import com.PrototypeServer.spring.service.CompanyService;
import com.PrototypeServer.spring.service.VehicleService;
import com.PrototypeServer.spring.service.UserService;
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
public class VehicleController {

    private VehicleService vehicleService;
    private CompanyService companyService;
    private UserService userService;

    @Autowired(required=true)
    @Qualifier(value="vehicleService")
    public void setVehicleService(VehicleService ps){
        this.vehicleService = ps;
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
    @RequestMapping(value = "/vehicle/create", method = RequestMethod.POST)
    public Object create(@RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="name") String name,
                         Model model) {

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

        //create new vehicle
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Vehicle vehicle = new Vehicle(company, name, dateFormat.format(new Date()));
        this.vehicleService.addVehicle(vehicle);
        return new SuccessResponse(0, user);
    }

    //DELETE
    @RequestMapping(value = "/vehicle/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="vehicle_id") int vehicleId,
                         Model model){

        Vehicle vehicle = this.vehicleService.getVehicleById(vehicleId);
        if(vehicle == null){
            return new ErrorResponse(3, "No vehicle found with this id");
        }
        if(companyId != vehicle.getCompany().getCompany_id()){
            return new ErrorResponse(3, "No vehicle found with this company id");
        }

        if(uniqueId != vehicle.getCompany().getUser().getUnique_id()){
            return new ErrorResponse(3, "No vehicle found with this unique id");
        }

        this.vehicleService.removeVehicle(vehicleId);
        return new SuccessResponse(0, new User());
    }

    //UPDATE
    @RequestMapping(value = "/vehicle/update", method = RequestMethod.POST)
    public Object create(@RequestParam(value="vehicle_id") int vehicleId,
                         @RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="name") String name,
                         Model model) {

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

        //create new vehicle
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Vehicle vehicle = new Vehicle(company, name, dateFormat.format(new Date()));
        vehicle.setVehicle_id(vehicleId);
        this.vehicleService.updateVehicle(vehicle);
        return new SuccessResponse(0, user);
    }
}
