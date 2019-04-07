package com.PrototypeServer.spring;

import com.PrototypeServer.spring.model.*;
import com.PrototypeServer.spring.service.CompanyService;
import com.PrototypeServer.spring.service.PropertyService;
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

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@RestController
public class PropertyController {

    private PropertyService propertyService;
    private CompanyService companyService;
    private UserService userService;

    @Autowired(required=true)
    @Qualifier(value="propertyService")
    public void setPropertyService(PropertyService ps){
        this.propertyService = ps;
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
    @RequestMapping(value = "/property/create", method = RequestMethod.POST)
    public Object create(@RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="street_name") String streetName,
                         @RequestParam(value="house_number") String houseNumber,
                         @RequestParam(value="post_code") String postCode,
                         @RequestParam(value="yearly_cost") int yearlyCost,
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

        //create new property
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Property property = new Property(company, streetName, houseNumber, postCode, yearlyCost, dateFormat.format(new Date()));
        this.propertyService.addProperty(property);
        return new SuccessResponse(0, user);
    }

    //DELETE
    @RequestMapping(value = "/property/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="property_id") int propertyId,
                         Model model){

        Property property = this.propertyService.getPropertyById(propertyId);
        if(property == null){
            return new ErrorResponse(3, "No property found with this id");
        }
        if(companyId != property.getCompany().getCompany_id()){
            return new ErrorResponse(3, "No property found with this company id");
        }

        if(uniqueId != property.getCompany().getUser().getUnique_id()){
            return new ErrorResponse(3, "No property found with this unique id");
        }

        this.propertyService.removeProperty(propertyId);
        return new SuccessResponse(0, new User());
    }

    //UPDATE
    @RequestMapping(value = "/property/update", method = RequestMethod.POST)
    public Object create(@RequestParam(value="property_id") int propertyId,
                         @RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="street_name") String streetName,
                         @RequestParam(value="house_number") String houseNumber,
                         @RequestParam(value="post_code") String postCode,
                         @RequestParam(value="yearly_cost") int yearlyCost,
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

        //create new property
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Property property = new Property(company, streetName, houseNumber, postCode, yearlyCost, dateFormat.format(new Date()));
        property.setProperty_id(propertyId);
        this.propertyService.updateProperty(property);
        return new SuccessResponse(0, user);
    }

    @RequestMapping(value = "/property/require", method = RequestMethod.POST)
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
                        for(Property property : ((Company) company).getProperties()){
                            JSONObject tmp = new JSONObject();
                            tmp.put("property_id", property.getProperty_id());
                            tmp.put("street_name", property.getStreet_name());
                            tmp.put("house_number", property.getHouse_number());
                            tmp.put("post_code", property.getPost_code());
                            tmp.put("yearly_cost", property.getYearly_cost());
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
