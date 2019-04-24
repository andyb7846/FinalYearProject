package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.Company;
import com.PrototypeServer.spring.model.User;
import com.PrototypeServer.spring.service.CompanyService;
import com.PrototypeServer.spring.service.UserService;
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
public class WebCompanyController {

    private CompanyService companyService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="companyService") //Define the name of the called function. This allows us to have more than 1.
    public void setCompanyService(CompanyService ps){
        this.companyService = ps;
    }

    private UserService userService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="userService") //Define the name of the called function. This allows us to have more than 1.
    public void setUserService(UserService ps){
        this.userService = ps;
    }

    @RequestMapping(value = "web/company", method = RequestMethod.GET)
    public String login(HttpSession session, Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        List<Company> companyList = this.companyService.listCompanies();
        model.addAttribute("companies", companyList);
        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "company";
    }

    @RequestMapping(value = "web/company/delete", method = RequestMethod.GET)
    public String delete(HttpSession session,
                         @RequestParam(value="company_id") int company_id,
                         Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(this.companyService.getCompanyById(company_id) != null){
            this.companyService.removeCompany(company_id);
        }

        return "redirect:/web/company";
    }

    @RequestMapping(value = "web/company/add", method = RequestMethod.POST)
    public String add(HttpSession session,
                      @RequestParam(value="user_id") int user_id,
                      @RequestParam(value="name") String name,
                      @RequestParam(value="yearly_income") int yearly_income,
                      Model model) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(name != "") {

            User user = null;
            user = this.userService.getUserById(user_id);
            if (user == null) {
                model.addAttribute("error", "No user found with this id");
                return "404"; // Verification Technique.
            }

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Company company = new Company(name,
                    yearly_income,
                    dateFormat.format(new Date()),
                    user);

            this.companyService.addCompany(company);
            return "redirect:/web/company";

        }
        else {
            model.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }

    @RequestMapping(value = "web/company/update", method = RequestMethod.POST)
    public String update(HttpSession session,
                         @RequestParam(value="company_id") int company_id,
                         @RequestParam(value="user_id") int user_id,
                         @RequestParam(value="name") String name,
                         @RequestParam(value="yearly_income") int yearly_income,
                         Model model) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(name != "") {

            Company company = this.companyService.getCompanyById(company_id);
            if (company == null) {
                model.addAttribute("error", "No company found with this id");
                return "404"; // Verification Technique.
            }

            User user = this.userService.getUserById(user_id);
            if (user == null) {
                model.addAttribute("error", "No user found with this id");
                return "404"; // Verification Technique.
            }

            company.setName(name);
            company.setUser(user);
            company.setYearly_income(yearly_income);
            this.companyService.updateCompany(company); //Add the company to the DB
            return "redirect:/web/company";

        }
        else {
            model.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }
}
