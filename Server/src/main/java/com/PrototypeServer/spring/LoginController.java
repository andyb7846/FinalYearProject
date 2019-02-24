package com.PrototypeServer.spring;

import com.PrototypeServer.spring.model.ErrorResponse;
import com.PrototypeServer.spring.model.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.PrototypeServer.spring.model.User;
import com.PrototypeServer.spring.service.UserService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class LoginController {

    private UserService userService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="userService") //Define the name of the called function. This allows us to have more than 1.
    public void setUserService(UserService ps){
        this.userService = ps;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST) //Defining the URL. Matches the URL Configured in AS
    public Object login(@RequestParam(value="username") String username, @RequestParam(value="password") String password, Model model) {

        if(username != null && password != null) {

            List<User> userList = this.userService.isExist(username);
            if (userList == null || userList.isEmpty()) {
                return new ErrorResponse(3, "username or password error");
            } else {
                String hashPassword = userList.get(0).getPassword();

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                //Successful Login
                if (passwordEncoder.matches(password, hashPassword) == true) { //Conformity with Security Procedures
                    return new SuccessResponse(0, userList.get(0));
                }
                //Failure Login
                else{
                    return new ErrorResponse(3, "username or password error");
                }
            }
        }
        else {
            return new ErrorResponse(2, "username or password should not be empty"); //Verification Statement
        }
    }

    // User Registration
    @RequestMapping(value= "/register") //Defining the URL. Matches the URL Configured in AppConfig in AS
    public  Object register(@RequestParam(value="username") String username, @RequestParam(value="password") String password, Model model){

        if(username != null && password != null) {

            List<User> userList = this.userService.isExist(username);
            if(userList == null || userList.isEmpty()){

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //Create new instance of encoder for password.
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                User user = new User(username,
                        passwordEncoder.encode(password),
                        String.valueOf(UUID.randomUUID()), //Format the data back to string type to store it as a string.
                        dateFormat.format(new Date()));

                this.userService.addUser(user); //Add the user to the DB
                return new SuccessResponse(0, user); //Inform user of successful registration.
            }
            else{
                return new ErrorResponse(1, "User already existed"); //Inform user of existing registration.
            }
        }
        else {
            return new ErrorResponse(2, "username or password should not be empty"); // Verification Technique.
        }
    }
}
