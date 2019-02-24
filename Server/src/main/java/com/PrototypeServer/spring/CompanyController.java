package com.PrototypeServer.spring;

import com.PrototypeServer.spring.model.Company;
import com.PrototypeServer.spring.model.ErrorResponse;
import com.PrototypeServer.spring.model.SuccessResponse;
import com.PrototypeServer.spring.model.User;
import com.PrototypeServer.spring.service.CompanyService;
import com.PrototypeServer.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class CompanyController {

    private CompanyService companyService;
    private UserService userService;

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

    @RequestMapping(value = "/company/create", method = RequestMethod.POST)
    public Object create(@RequestParam(value="company_name") String companyName, @RequestParam(value="unique_id") String uniqueId, Model model) {

        if(companyName != null && uniqueId != null) {

            User user = this.userService.getUserByUniqueId(uniqueId);
            if (user == null) {
                return new ErrorResponse(3, "No user found with this unique_id");
            } else {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Company company = new Company(companyName,
                            dateFormat.format(new Date()),
                            user);

                this.companyService.addCompany(company);
                return new SuccessResponse(0, user);
            }
        }
        else {
            return new ErrorResponse(2, "company name or unique id should not be empty"); //Verification Statement
        }
    }

}
