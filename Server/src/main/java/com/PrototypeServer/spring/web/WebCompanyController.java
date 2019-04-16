package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.Company;
import com.PrototypeServer.spring.service.CompanyService;
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
public class WebCompanyController {

    private CompanyService companyService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="companyService") //Define the name of the called function. This allows us to have more than 1.
    public void setCompanyService(CompanyService ps){
        this.companyService = ps;
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
}
