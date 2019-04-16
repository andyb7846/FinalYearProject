package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.Property;
import com.PrototypeServer.spring.service.PropertyService;
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
public class WebPropertyController {

    private PropertyService propertyService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="propertyService") //Define the name of the called function. This allows us to have more than 1.
    public void setPropertyService(PropertyService ps){
        this.propertyService = ps;
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
}
