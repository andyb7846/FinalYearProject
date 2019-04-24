package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.Company;
import com.PrototypeServer.spring.model.Device;
import com.PrototypeServer.spring.model.Property;
import com.PrototypeServer.spring.service.CompanyService;
import com.PrototypeServer.spring.service.DeviceService;
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
public class WebDeviceController {

    private DeviceService deviceService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="deviceService") //Define the name of the called function. This allows us to have more than 1.
    public void setDeviceService(DeviceService ps){
        this.deviceService = ps;
    }


    private CompanyService companyService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="companyService") //Define the name of the called function. This allows us to have more than 1.
    public void setCompanyService(CompanyService ps){
        this.companyService = ps;
    }

    @RequestMapping(value = "web/device", method = RequestMethod.GET)
    public String login(HttpSession session, Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        List<Device> deviceList = this.deviceService.listDevices();
        model.addAttribute("devices", deviceList);
        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "device";
    }

    @RequestMapping(value = "web/device/delete", method = RequestMethod.GET)
    public String delete(HttpSession session,
                         @RequestParam(value="device_id") int device_id,
                         Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(this.deviceService.getDeviceById(device_id) != null){
            this.deviceService.removeDevice(device_id);
        }

        return "redirect:/web/device";
    }

    @RequestMapping(value = "web/device/add", method = RequestMethod.POST)
    public String add(HttpSession session,
                      @RequestParam(value="company_id") int company_id,
                      @RequestParam(value="brand") String brand,
                      @RequestParam(value="model") String model,
                      @RequestParam(value="yearly_cost") int yearly_cost,
                      Model model2) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(brand != "" && model != "") {

            Company company = this.companyService.getCompanyById(company_id);
            if (company == null) {
                model2.addAttribute("error", "No company found with this id");
                return "404"; // Verification Technique.
            }

            //create new device
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Device device = new Device(company, brand, model, yearly_cost, dateFormat.format(new Date()));
            this.deviceService.addDevice(device);
            return "redirect:/web/device";

        }
        else {
            model2.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }

    @RequestMapping(value = "web/device/update", method = RequestMethod.POST)
    public String update(HttpSession session,
                         @RequestParam(value="device_id") int device_id,
                         @RequestParam(value="company_id") int company_id,
                         @RequestParam(value="brand") String brand,
                         @RequestParam(value="model") String model,
                         @RequestParam(value="yearly_cost") int yearly_cost,
                         Model model2) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(brand != "" && model != "") {

            Device device = this.deviceService.getDeviceById(device_id);
            if (device == null) {
                model2.addAttribute("error", "No device found with this id");
                return "404"; // Verification Technique.
            }

            Company company = this.companyService.getCompanyById(company_id);
            if (company == null) {
                model2.addAttribute("error", "No company found with this id");
                return "404"; // Verification Technique.
            }

            //create new device
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            device.setCompany(company);
            device.setBrand(brand);
            device.setModel(model);
            device.setYearly_cost(yearly_cost);
            this.deviceService.updateDevice(device);
            return "redirect:/web/device";

        }
        else {
            model2.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }
}
