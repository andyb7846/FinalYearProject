package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.*;
import com.PrototypeServer.spring.service.CompanyService;
import com.PrototypeServer.spring.service.EmployeeService;
import com.PrototypeServer.spring.web.model.WebEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class WebEmployeeController {

    private EmployeeService employeeService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="employeeService") //Define the name of the called function. This allows us to have more than 1.
    public void setEmployeeService(EmployeeService ps){
        this.employeeService = ps;
    }

    private CompanyService companyService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="companyService") //Define the name of the called function. This allows us to have more than 1.
    public void setCompanyService(CompanyService ps){
        this.companyService = ps;
    }
    

    @RequestMapping(value = "web/employee", method = RequestMethod.GET)
    public String login(HttpSession session, Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        List<Employee> employeeList = this.employeeService.listEmployees();
        List<WebEmployee> newEmployeeList = new ArrayList<WebEmployee>();

        try {
            File file = new File("hello");
            FileInputStream in = new FileInputStream(file);
            byte[] content = new byte[(int) file.length()];
            in.read(content);

            SecretKey key = new SecretKeySpec(content, "DES");
            Cipher desCipher;
            desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            desCipher.init(Cipher.DECRYPT_MODE, key);

            for (Employee employee : employeeList) {
                WebEmployee newEmployee = new WebEmployee(employee.getCompany(),
                        new String(desCipher.doFinal(employee.getForename())),
                        new String(desCipher.doFinal(employee.getSurname())),
                        employee.getJob_name(),
                        employee.getSalary(),
                        employee.getTax(),
                        employee.getCreate_time());
                newEmployee.setEmployee_id(employee.getEmployee_id());
                newEmployeeList.add(newEmployee);
            }
        }catch (Exception e){
            return "employee";
        }
        model.addAttribute("employees", newEmployeeList);
        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "employee";
    }

    @RequestMapping(value = "web/employee/delete", method = RequestMethod.GET)
    public String delete(HttpSession session,
                         @RequestParam(value="employee_id") int employee_id,
                         Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(this.employeeService.getEmployeeById(employee_id) != null){
            this.employeeService.removeEmployee(employee_id);
        }

        return "redirect:/web/employee";
    }

    @RequestMapping(value = "web/employee/add", method = RequestMethod.POST)
    public String add(HttpSession session,
                      @RequestParam(value="company_id") int company_id,
                      @RequestParam(value="forename") String forename,
                      @RequestParam(value="surname") String surname,
                      @RequestParam(value="title") String title,
                      @RequestParam(value="salary") int salary,
                      Model model) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(forename != "" && surname != "" && title != "") {

            Company company = this.companyService.getCompanyById(company_id);
            if (company == null) {
                model.addAttribute("error", "No company found with this id");
                return "404"; // Verification Technique.
            }

            int tax = caculateTax(salary);

            try {
                File file = new File("hello");
                FileInputStream in = new FileInputStream(file);
                byte[] content = new byte[(int) file.length()];
                in.read(content);

                SecretKey key = new SecretKeySpec(content, "DES");
                Cipher desCipher;
                desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                desCipher.init(Cipher.ENCRYPT_MODE, key);

                byte[] byteForename = desCipher.doFinal(forename.getBytes());
                byte[] byteSurname = desCipher.doFinal(surname.getBytes());

                //create new employee
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Employee employee = new Employee(company, byteForename, byteSurname, title, salary, tax, dateFormat.format(new Date()));
                this.employeeService.addEmployee(employee);
                return "redirect:/web/employee";

            }catch (Exception e){
                model.addAttribute("error", "key error");
                return "404"; // Verification Technique.
            }

        }
        else {
            model.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }

    @RequestMapping(value = "web/employee/update", method = RequestMethod.POST)
    public String update(HttpSession session,
                         @RequestParam(value="employee_id") int employee_id,
                         @RequestParam(value="company_id") int company_id,
                         @RequestParam(value="forename") String forename,
                         @RequestParam(value="surname") String surname,
                         @RequestParam(value="title") String title,
                         @RequestParam(value="salary") int salary,
                         Model model) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(forename != "" && surname != "" && title != "") {

            Employee employee = this.employeeService.getEmployeeById(employee_id);
            if (employee == null) {
                model.addAttribute("error", "No employee found with this id");
                return "404"; // Verification Technique.
            }

            Company company = this.companyService.getCompanyById(company_id);
            if (company == null) {
                model.addAttribute("error", "No company found with this id");
                return "404"; // Verification Technique.
            }

            int tax = caculateTax(salary);

            try {
                File file = new File("hello");
                FileInputStream in = new FileInputStream(file);
                byte[] content = new byte[(int) file.length()];
                in.read(content);

                SecretKey key = new SecretKeySpec(content, "DES");
                Cipher desCipher;
                desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                desCipher.init(Cipher.ENCRYPT_MODE, key);

                byte[] byteForename = desCipher.doFinal(forename.getBytes());
                byte[] byteSurname = desCipher.doFinal(surname.getBytes());

                //create new employee
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                employee.setCompany(company);
                employee.setForename(byteForename);
                employee.setSurname(byteSurname);
                employee.setJob_name(title);
                employee.setSalary(salary);
                employee.setTax(tax);
                this.employeeService.updateEmployee(employee);
                return "redirect:/web/employee";

            }catch (Exception e){
                model.addAttribute("error", "key error");
                return "404"; // Verification Technique.
            }

        }
        else {
            model.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }

    public int caculateTax(int salary){
        if(salary <= 11850){
            return 0;
        }
        else if(salary <= 46350){
            return (int)((salary - 11850)*0.2);
        }
        else{
            return (int)((salary - 46350)*0.4);
        }
    }
}
