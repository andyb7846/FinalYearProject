package com.PrototypeServer.spring;

import com.PrototypeServer.spring.model.*;
import com.PrototypeServer.spring.service.CompanyService;
import com.PrototypeServer.spring.service.EmployeeService;
import com.PrototypeServer.spring.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
                         @RequestParam(value="company_id") String companyId,
                         @RequestParam(value="forename") String forename,
                         @RequestParam(value="surname") String surname,
                         @RequestParam(value="title") String jobName,
                         @RequestParam(value="tax_id") String taxId,
                         @RequestParam(value="gov_tax_code") String govTaxCode,
                         @RequestParam(value="salary") String salary,
                         //@RequestParam(value="tax") int tax,
                         Model model) {

        User user = this.userService.getUserByUniqueId(uniqueId);
        if (user == null) {
            return new ErrorResponse(3, "No user found with this unique_id");
        }

        Company company = null;
        for(Iterator iterator = user.getCompanies().iterator(); iterator.hasNext();) {
            Company comp = (Company) iterator.next();
            if (Integer.parseInt(companyId) == comp.getCompany_id()) {
                company = comp;
                break;
            }
        }

        if(company == null){
            return new ErrorResponse(3, "No company found with this unique_id");
        }

        int tax = caculateTax(Integer.parseInt(salary));

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
            Employee employee = new Employee(company, byteForename, byteSurname, jobName, taxId, govTaxCode, Integer.parseInt(salary), tax, dateFormat.format(new Date()));
            this.employeeService.addEmployee(employee);
            return new SuccessResponse(0, user);

        }catch (Exception e){
            return new ErrorResponse(2, "key error");
        }

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
                         @RequestParam(value="title") String jobName,
                         @RequestParam(value="tax_id") String taxId,
                         @RequestParam(value="gov_tax_code") String govTaxCode,
                         @RequestParam(value="salary") int salary,
                         //@RequestParam(value="tax") int tax,
                         Model model) {

        User user = this.userService.getUserByUniqueId(uniqueId);
        if (user == null) {
            return new ErrorResponse(3, "No user found with this unique_id" + uniqueId);
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
            Employee employee = new Employee(company, byteForename, byteSurname, jobName, taxId, govTaxCode, salary, tax, dateFormat.format(new Date()));
            employee.setEmployee_id(employeeId);
            this.employeeService.updateEmployee(employee);
            return new SuccessResponse(0, user);

        }catch (Exception e){
            return new ErrorResponse(2, "key error");
        }

    }

    @RequestMapping(value = "/employee/require", method = RequestMethod.POST)
    public Object require(@RequestParam(value="unique_id") String uniqueId, @RequestParam(value="company_id") int companyId, Model model) {

        if(uniqueId != null) {

            User user = this.userService.getUserByUniqueId(uniqueId);
            if (user == null) {
                return new ErrorResponse(3, "No user found with this unique_id");
            } else {

                try {
                    File file = new File("hello");
                    FileInputStream in = new FileInputStream(file);
                    byte[] content = new byte[(int) file.length()];
                    in.read(content);

                    SecretKey key = new SecretKeySpec(content, "DES");
                    Cipher desCipher;
                    desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                    desCipher.init(Cipher.DECRYPT_MODE, key);

                    for(Object company : user.getCompanies()){
                        if(((Company)company).getCompany_id() == companyId){
                            //return new ErrorResponse(4, "actually succeeded");
                            JSONArray ja = new JSONArray();
                            for(Employee employee : ((Company) company).getEmployees()){
                                JSONObject tmp = new JSONObject();
                                tmp.put("employee_id", employee.getEmployee_id());
                                tmp.put("forename", new String(desCipher.doFinal(employee.getForename())));
                                tmp.put("surname", new String(desCipher.doFinal(employee.getSurname())));
                                tmp.put("title", employee.getJob_name());
                                tmp.put("tax_id", employee.getTax_id());
                                tmp.put("gov_tax_code", employee.getGoveronment_tax_code());
                                tmp.put("salary", employee.getSalary());
                                tmp.put("tax", employee.getTax());
                                ja.put(tmp);

                            }
                            return ja.toString();
                        }
                    }
                }catch (Exception e){
                    return new ErrorResponse(2, "key error");
                }


                return new ErrorResponse(2, "No company found with this company_id and unique_id"); //Verification Statement
            }
        }
        else {
            return new ErrorResponse(2, "company id or unique id should not be empty"); //Verification Statement
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
