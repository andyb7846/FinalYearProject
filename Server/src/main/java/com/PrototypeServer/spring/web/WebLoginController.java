package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.ErrorResponse;
import com.PrototypeServer.spring.model.SuccessResponse;
import com.PrototypeServer.spring.model.User;
import com.PrototypeServer.spring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.UUID;

@Controller
public class WebLoginController {


    private AdminService adminService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="adminService") //Define the name of the called function. This allows us to have more than 1.
    public void setAdminService(AdminService ps){
        this.adminService = ps;
    }


    @RequestMapping(value = "web/login", method = RequestMethod.GET)
    public String login(HttpSession session, Model model) {
        if(session.getAttribute("admin") != null){
            return "redirect:/web/user";
        }
        model.addAttribute("title", "Welcome Back!");
        return "login";
    }


    @RequestMapping(value = "web/login", method = RequestMethod.POST)
    public String login(HttpSession session,
                        @RequestParam(value="email") String email,
                        @RequestParam(value="password") String password,
                        Model model) {

        if(email != null && password != null) {

            List<Admin> adminList = this.adminService.isExist(email);
            if (adminList == null || adminList.isEmpty()) {
                model.addAttribute("title", "Admin not exist");
                return "login";
            } else {
                String hashPassword = adminList.get(0).getPassword();

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                //Successful Login
                if (passwordEncoder.matches(password, hashPassword) == true) { //Conformity with Security Procedures
                    session.setAttribute("admin", adminList.get(0));
                    return "redirect:/web/user";
                }
                //Failure Login
                else{
                    model.addAttribute("title", "Password Error");
                    return "login";
                }
            }
        }
        //model.addAttribute("name", name);
        model.addAttribute("title", "Email and Password should not be Empty");
        return "login";
    }


    @RequestMapping(value = "web/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("title", "Create an Account!");
        return "register";
    }

    // User Registration
    @RequestMapping(value= "web/register", method = RequestMethod.POST) //Defining the URL. Matches the URL Configured in AppConfig in AS
    public  Object register(@RequestParam(value="name") String name,
                            @RequestParam(value="password") String password,
                            @RequestParam(value="email") String email,
                            Model model){

        if(name == "" || password == "" || email == ""){
            model.addAttribute("title", "Empty Field not allowed");
            return "register"; //Inform admin of successful registration.
        }
        if(name != null && password != null && email != null) {

            List<Admin> adminList = this.adminService.isExist(email);
            if(adminList == null || adminList.isEmpty()){

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //Create new instance of encoder for password.
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                Admin admin = new Admin(name,
                        passwordEncoder.encode(password),
                        email,
                        dateFormat.format(new Date()));

                this.adminService.addAdmin(admin); //Add the admin to the DB

                model.addAttribute("title", "Welcome Back!");
                return "login"; //Inform admin of successful registration.
            }
            else{
                model.addAttribute("title", "This Email has been used!");
                return "register"; //Inform admin of successful registration.
            }
        }
        else {
            model.addAttribute("title", "Empty Field not allowed");
            return "register"; //Inform admin of successful registration.
        }
    }
    @RequestMapping(value = "web/logout", method = RequestMethod.GET)
    public String logout(HttpSession session, Model model){
        session.invalidate();
        return "redirect:/web/login";
    }
}
