package com.PrototypeServer.spring;

import com.PrototypeServer.spring.model.*;
import com.PrototypeServer.spring.service.CompanyService;
import com.PrototypeServer.spring.service.EmployeeService;
import com.PrototypeServer.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;
    private CompanyService companyService;
    private UserService userService;

    @Autowired(required=true)
    @Qualifier(value="employeeService")
    public void setEmployeeService(EmployeeService ps){
        this.employeeService = ps;
    }

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

    //CREATE
    @RequestMapping(value = "/employee/create", method = RequestMethod.POST)
    public Object create(@RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="forename") String forename,
                         @RequestParam(value="surname") String surname,
                         @RequestParam(value="job_name") String jobName,
                         @RequestParam(value="tax_id") String taxId,
                         @RequestParam(value="goveronment_tax_code") String govTaxCode,
                         @RequestParam(value="salary") int salary,
                         @RequestParam(value="tax") int tax,
                         Model model) {

        User user = this.userService.getUserByUniqueId(uniqueId);
        if (user == null) {
            return new ErrorResponse(3, "No user found with this unique_id");
        }

        Company company = null;
        for(Iterator iterator = user.getCompanies().iterator(); iterator.hasNext();) {
            Company comp = (Company) iterator.next();
            if (companyId == comp.getCompany_id()) {
                company = comp;
                break;
            }
        }

        if(company == null){
            return new ErrorResponse(3, "No company found with this unique_id");
        }

        //create new employee
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Employee employee = new Employee(user, company, forename, surname, jobName, taxId, govTaxCode, salary, tax, dateFormat.format(new Date()));
        this.employeeService.addEmployee(employee);
        return new SuccessResponse(0, user);
    }

    //DELETE
    @RequestMapping(value = "/employee/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="employee_id") int employeeId,
                         Model model){

        Employee employee = this.employeeService.getEmployeeById(employeeId);
        if(employee == null){
            return new ErrorResponse(3, "No employee found with this id");
        }
        if(companyId != employee.getCompany().getCompany_id()){
            return new ErrorResponse(3, "No employee found with this company id");
        }

        if(uniqueId != employee.getCompany().getUser().getUnique_id()){
            return new ErrorResponse(3, "No employee found with this unique id");
        }

        this.employeeService.removeEmployee(employeeId);
        return new SuccessResponse(0, new User());
    }

    //UPDATE
    @RequestMapping(value = "/employee/update", method = RequestMethod.POST)
    public Object create(@RequestParam(value="employee_id") int employeeId,
                         @RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         @RequestParam(value="forename") String forename,
                         @RequestParam(value="surname") String surname,
                         @RequestParam(value="job_name") String jobName,
                         @RequestParam(value="tax_id") String taxId,
                         @RequestParam(value="goveronment_tax_code") String govTaxCode,
                         @RequestParam(value="salary") int salary,
                         @RequestParam(value="tax") int tax,
                         Model model) {

        User user = this.userService.getUserByUniqueId(uniqueId);
        if (user == null) {
            return new ErrorResponse(3, "No user found with this unique_id");
        }

        Company company = null;
        for(Iterator iterator = user.getCompanies().iterator(); iterator.hasNext();) {
            Company comp = (Company) iterator.next();
            if (companyId == comp.getCompany_id()) {
                company = comp;
                break;
            }
        }

        if(company == null){
            return new ErrorResponse(3, "No company found with this unique_id");
        }

        //create new employee
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Employee employee = new Employee(user, company, forename, surname, jobName, taxId, govTaxCode, salary, tax, dateFormat.format(new Date()));
        employee.setEmployee_id(employeeId);
        this.employeeService.updateEmployee(employee);
        return new SuccessResponse(0, user);
    }
}
