package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.Company;
import com.PrototypeServer.spring.model.Property;
import com.PrototypeServer.spring.model.Vehicle;
import com.PrototypeServer.spring.service.CompanyService;
import com.PrototypeServer.spring.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class WebVehicleController {

    private VehicleService vehicleService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="vehicleService") //Define the name of the called function. This allows us to have more than 1.
    public void setVehicleService(VehicleService ps){
        this.vehicleService = ps;
    }

    private CompanyService companyService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="companyService") //Define the name of the called function. This allows us to have more than 1.
    public void setCompanyService(CompanyService ps){
        this.companyService = ps;
    }

    @RequestMapping(value = "web/vehicle", method = RequestMethod.GET)
    public String login(HttpSession session, Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        List<Vehicle> vehicleList = this.vehicleService.listVehicles();
        model.addAttribute("vehicles", vehicleList);
        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "vehicle";
    }

    @RequestMapping(value = "web/vehicle/delete", method = RequestMethod.GET)
    public String delete(HttpSession session,
                         @RequestParam(value="vehicle_id") int vehicle_id,
                         Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(this.vehicleService.getVehicleById(vehicle_id) != null){
            this.vehicleService.removeVehicle(vehicle_id);
        }

        return "redirect:/web/vehicle";
    }

    @RequestMapping(value = "web/vehicle/add", method = RequestMethod.POST)
    public String add(HttpSession session,
                      @RequestParam(value="company_id") int company_id,
                      @RequestParam(value="manufacturer") String manufacturer,
                      @RequestParam(value="model") String model,
                      @RequestParam(value="registration") String registration,
                      @RequestParam(value="yearly_cost") int yearly_cost,
                      Model model2) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(manufacturer != "" && model != "" && registration != "") {

            Company company = this.companyService.getCompanyById(company_id);
            if (company == null) {
                model2.addAttribute("error", "No company found with this id");
                return "404"; // Verification Technique.
            }

            //create new vehicle
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Vehicle vehicle = new Vehicle(company, manufacturer, model, registration, yearly_cost, dateFormat.format(new Date()));
            this.vehicleService.addVehicle(vehicle);
            return "redirect:/web/vehicle";

        }
        else {
            model2.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }

    @RequestMapping(value = "web/vehicle/update", method = RequestMethod.POST)
    public String update(HttpSession session,
                         @RequestParam(value="vehicle_id") int vehicle_id,
                         @RequestParam(value="company_id") int company_id,
                         @RequestParam(value="manufacturer") String manufacturer,
                         @RequestParam(value="model") String model,
                         @RequestParam(value="registration") String registration,
                         @RequestParam(value="yearly_cost") int yearly_cost,
                         Model model2) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(manufacturer != "" && model != "" && registration != "") {

            Vehicle vehicle = this.vehicleService.getVehicleById(vehicle_id);
            if (vehicle == null) {
                model2.addAttribute("error", "No vehicle found with this id");
                return "404"; // Verification Technique.
            }

            Company company = this.companyService.getCompanyById(company_id);
            if (company == null) {
                model2.addAttribute("error", "No company found with this id");
                return "404"; // Verification Technique.
            }

            //create new vehicle
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            vehicle.setCompany(company);
            vehicle.setManufacturer(manufacturer);
            vehicle.setModel(model);
            vehicle.setRegistration(registration);
            vehicle.setYearly_cost(yearly_cost);
            this.vehicleService.updateVehicle(vehicle);
            return "redirect:/web/vehicle";

        }
        else {
            model2.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }
}
