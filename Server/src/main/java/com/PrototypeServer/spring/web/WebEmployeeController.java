package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.Employee;
import com.PrototypeServer.spring.model.ErrorResponse;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebEmployeeController {

    private EmployeeService employeeService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="employeeService") //Define the name of the called function. This allows us to have more than 1.
    public void setEmployeeService(EmployeeService ps){
        this.employeeService = ps;
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
}
