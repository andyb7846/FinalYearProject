package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.Company;
import com.PrototypeServer.spring.model.Employee;
import com.PrototypeServer.spring.model.Property;
import com.PrototypeServer.spring.service.CompanyService;
import com.PrototypeServer.spring.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class WebPropertyController {

    private PropertyService propertyService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="propertyService") //Define the name of the called function. This allows us to have more than 1.
    public void setPropertyService(PropertyService ps){
        this.propertyService = ps;
    }


    private CompanyService companyService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="companyService") //Define the name of the called function. This allows us to have more than 1.
    public void setCompanyService(CompanyService ps){
        this.companyService = ps;
    }


    @RequestMapping(value = "web/property", method = RequestMethod.GET)
    public String login(HttpSession session, Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        List<Property> propertyList = this.propertyService.listProperties();
        model.addAttribute("properties", propertyList);
        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "property";
    }

    @RequestMapping(value = "web/property/delete", method = RequestMethod.GET)
    public String delete(HttpSession session,
                         @RequestParam(value="property_id") int property_id,
                         Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(this.propertyService.getPropertyById(property_id) != null){
            this.propertyService.removeProperty(property_id);
        }

        return "redirect:/web/property";
    }

    @RequestMapping(value = "web/property/add", method = RequestMethod.POST)
    public String add(HttpSession session,
                      @RequestParam(value="company_id") int company_id,
                      @RequestParam(value="street_name") String street_name,
                      @RequestParam(value="house_number") String house_number,
                      @RequestParam(value="post_code") String post_code,
                      @RequestParam(value="yearly_cost") int yearly_cost,
                      Model model) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(street_name != "" && house_number != "" && post_code != "") {

            Company company = this.companyService.getCompanyById(company_id);
            if (company == null) {
                model.addAttribute("error", "No company found with this id");
                return "404"; // Verification Technique.
            }
            
            //create new property
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Property property = new Property(company, street_name, house_number, post_code, yearly_cost, dateFormat.format(new Date()));
            this.propertyService.addProperty(property);
            return "redirect:/web/property";

        }
        else {
            model.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }

    @RequestMapping(value = "web/property/update", method = RequestMethod.POST)
    public String update(HttpSession session,
                         @RequestParam(value="property_id") int property_id,
                         @RequestParam(value="company_id") int company_id,
                         @RequestParam(value="street_name") String street_name,
                         @RequestParam(value="house_number") String house_number,
                         @RequestParam(value="post_code") String post_code,
                         @RequestParam(value="yearly_cost") int yearly_cost,
                         Model model) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(street_name != "" && house_number != "" && post_code != "") {

            Property property = this.propertyService.getPropertyById(property_id);
            if (property == null) {
                model.addAttribute("error", "No property found with this id");
                return "404"; // Verification Technique.
            }

            Company company = this.companyService.getCompanyById(company_id);
            if (company == null) {
                model.addAttribute("error", "No company found with this id");
                return "404"; // Verification Technique.
            }

            //create new property
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            property.setCompany(company);
            property.setStreet_name(street_name);
            property.setHouse_number(house_number);
            property.setPost_code(post_code);
            property.setYearly_cost(yearly_cost);
            this.propertyService.updateProperty(property);
            return "redirect:/web/property";

        }
        else {
            model.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }
}
