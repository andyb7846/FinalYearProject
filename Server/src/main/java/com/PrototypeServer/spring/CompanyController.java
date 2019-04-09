package com.PrototypeServer.spring;

import com.PrototypeServer.spring.model.*;
import com.PrototypeServer.spring.service.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RestController
public class CompanyController {

    private CompanyService companyService;
    private UserService userService;
    private EmployeeService employeeService;
    private PropertyService propertyService;
    private DeviceService deviceService;
    private VehicleService vehicleService;

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

    @Autowired(required=true)
    @Qualifier(value="employeeService")
    public void setEmployeeService(EmployeeService ps){
        this.employeeService = ps;
    }

    @Autowired(required=true)
    @Qualifier(value="propertyService")
    public void setPropertyService(PropertyService ps){
        this.propertyService = ps;
    }

    @Autowired(required=true)
    @Qualifier(value="deviceService")
    public void setDeviceService(DeviceService ps){
        this.deviceService = ps;
    }

    @Autowired(required=true)
    @Qualifier(value="vehicleService")
    public void setVehicleService(VehicleService ps){
        this.vehicleService = ps;
    }

    @RequestMapping(value = "/company/create", method = RequestMethod.POST)
    public Object create(@RequestParam(value="company_name") String companyName,
                         @RequestParam(value="income") int income,
                         @RequestParam(value="unique_id") String uniqueId, Model model) {

        if(companyName != null && uniqueId != null) {

            User user = this.userService.getUserByUniqueId(uniqueId);
            if (user == null) {
                return new ErrorResponse(3, "No user found with this unique_id");
            } else {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Company company = new Company(companyName,
                            income,
                            dateFormat.format(new Date()),
                            user);

                this.companyService.addCompany(company);

                /*
                try {
                    SecretKey key = KeyGenerator.getInstance("DES").generateKey();
                    Cipher desCipher;
                    desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
                    desCipher.init(Cipher.ENCRYPT_MODE, key);

                    byte[] text = "No body can see me".getBytes();
                    byte[] textEncrypted = desCipher.doFinal(text);

                    desCipher.init(Cipher.DECRYPT_MODE, key);
                    byte[] textDecrypted = desCipher.doFinal(textEncrypted);

                    byte[] byteKey = key.getEncoded();

                    File file = new File("hello");
                    FileInputStream in = new FileInputStream(file);
                    byte[] content = new byte[(int)file.length()];
                    in.read(content);

                    if(file.createNewFile()){
                        FileOutputStream out = new FileOutputStream(file);
                        out.write(byteKey);
                        out.close();

                        return new ErrorResponse(2, file.getAbsolutePath());
                    }

                    return new ErrorResponse(2, "create file failed");


                }
                catch (Exception e){
                    return new ErrorResponse(2, "No such algorithm"); //Verification Statement
                }
                */

                return new SuccessResponse(0, user);
            }
        }
        else {
            return new ErrorResponse(2, "company name or unique id should not be empty"); //Verification Statement
        }
    }


    @RequestMapping(value = "/company/require", method = RequestMethod.POST)
    public Object require(@RequestParam(value="unique_id") String uniqueId, Model model) {

        if(uniqueId != null) {

            User user = this.userService.getUserByUniqueId(uniqueId);
            if (user == null) {
                return new ErrorResponse(3, "No user found with this unique_id");
            } else {
                //return new ErrorResponse(4, "Acutally succeed");


                List<Company> companies = companyService.getCompaniesByUserId(user.getUser_id());

                JSONArray ja = new JSONArray();
                for(Company company: companies){
                    JSONObject tmp = new JSONObject();
                    tmp.put("company_id", company.getCompany_id());
                    tmp.put("name", company.getName());
                    tmp.put("income", company.getYearly_income());
                    /*
                    tmp.put("employees", company.getEmployees().size());
                    tmp.put("properties", company.getProperties().size());
                    tmp.put("devices", company.getDevices().size());
                    tmp.put("vehicles", company.getVehicles().size());
                    */
                    tmp.put("employees", employeeService.getEmployeesByCompanyId(company.getCompany_id()).size());
                    tmp.put("properties", propertyService.getPropertiesByCompanyId(company.getCompany_id()).size());
                    tmp.put("devices", deviceService.getDevicesByCompanyId(company.getCompany_id()).size());
                    tmp.put("vehicles", vehicleService.getVehiclesByCompanyId(company.getCompany_id()).size());

                    int outcome = 0;
                    Collection<Employee> employees = employeeService.getEmployeesByCompanyId(company.getCompany_id());
                    for(Employee employee: employees){
                        outcome += employee.getSalary();
                    }

                    Collection<Property> propertys = propertyService.getPropertiesByCompanyId(company.getCompany_id());
                    for(Property property: propertys){
                        outcome += property.getYearly_cost();
                    }

                    Collection<Device> devices = deviceService.getDevicesByCompanyId(company.getCompany_id());
                    for(Device device: devices){
                        outcome += device.getYearly_cost();
                    }

                    Collection<Vehicle> vehicles = vehicleService.getVehiclesByCompanyId(company.getCompany_id());
                    for(Vehicle vehicle: vehicles){
                        outcome += vehicle.getYearly_cost();
                    }

                    tmp.put("benefit", company.getYearly_income()-outcome);
                    
                    ja.put(tmp);
                }

                return ja.toString();
            }
        }
        else {
            return new ErrorResponse(2, "company name or unique id should not be empty"); //Verification Statement
        }
    }

    //DELETE
    @RequestMapping(value = "/company/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam(value="unique_id") String uniqueId,
                         @RequestParam(value="company_id") int companyId,
                         Model model){

        if(uniqueId != null) {

            User user = this.userService.getUserByUniqueId(uniqueId);
            if (user == null) {
                return new ErrorResponse(3, "No user found with this unique_id");
            } else {
                //return new ErrorResponse(4, "Acutally succeed");

                List<Company> companies = companyService.getCompaniesByUserId(user.getUser_id());


                for(Company company: companies){
                    if(company.getCompany_id() == companyId){
                        user.getCompanies().remove(company);
                        companyService.removeCompany(companyId);
                        return new SuccessResponse(0, user);
                    }
                }

                return new ErrorResponse(2, "no company found for this unique id");
            }
        }
        else {
            return new ErrorResponse(2, "company id or unique id should not be empty"); //Verification Statement
        }
    }

}
