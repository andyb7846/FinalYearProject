package com.PrototypeServer.spring;

import com.PrototypeServer.spring.model.*;
import com.PrototypeServer.spring.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
public class StatisticsController {

    private CompanyService companyService;
    private UserService userService;
    private EmployeeService employeeService;
    private PropertyService propertyService;
    private DeviceService deviceService;
    private VehicleService vehicleService;

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

    @Autowired(required=true)
    @Qualifier(value="employeeService")
    public void setEmployeeService(EmployeeService ps){
        this.employeeService = ps;
    }

    @Autowired(required=true)
    @Qualifier(value="propertyService")
    public void setPropertyService(PropertyService ps){
        this.propertyService = ps;
    }

    @Autowired(required=true)
    @Qualifier(value="deviceService")
    public void setDeviceService(DeviceService ps){
        this.deviceService = ps;
    }

    @Autowired(required=true)
    @Qualifier(value="vehicleService")
    public void setVehicleService(VehicleService ps){
        this.vehicleService = ps;
    }


    @RequestMapping(value = "/statistics/require", method = RequestMethod.POST)
    public Object require(@RequestParam(value="unique_id") String uniqueId, Model model) {

        if(uniqueId != null) {

            User user = this.userService.getUserByUniqueId(uniqueId);
            if (user == null) {
                return new ErrorResponse(3, "No user found with this unique_id");
            } else {
                //return new ErrorResponse(4, "Acutally succeed");


                List<Company> companies = companyService.getCompaniesByUserId(user.getUser_id());

                JSONArray ja = new JSONArray();
                for(Company company: companies){
                    JSONObject tmp = new JSONObject();
                    tmp.put("company_id", company.getCompany_id());
                    tmp.put("name", company.getName());
                    tmp.put("employees", employeeService.getEmployeesByCompanyId(company.getCompany_id()).size());
                    tmp.put("properties", propertyService.getPropertiesByCompanyId(company.getCompany_id()).size());
                    tmp.put("devices", deviceService.getDevicesByCompanyId(company.getCompany_id()).size());
                    tmp.put("vehicles", vehicleService.getVehiclesByCompanyId(company.getCompany_id()).size());

                    ja.put(tmp);
                }

                return ja.toString();
            }
        }
        else {
            return new ErrorResponse(2, "company name or unique id should not be empty"); //Verification Statement
        }
    }

}
