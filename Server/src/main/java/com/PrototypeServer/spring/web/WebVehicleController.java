package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.Vehicle;
import com.PrototypeServer.spring.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class WebVehicleController {

    private VehicleService vehicleService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="vehicleService") //Define the name of the called function. This allows us to have more than 1.
    public void setVehicleService(VehicleService ps){
        this.vehicleService = ps;
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
}
