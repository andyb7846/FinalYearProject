package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.Device;
import com.PrototypeServer.spring.service.DeviceService;
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
public class WebDeviceController {

    private DeviceService deviceService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="deviceService") //Define the name of the called function. This allows us to have more than 1.
    public void setDeviceService(DeviceService ps){
        this.deviceService = ps;
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
}
