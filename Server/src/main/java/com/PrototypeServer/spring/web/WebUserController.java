package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.ErrorResponse;
import com.PrototypeServer.spring.model.SuccessResponse;
import com.PrototypeServer.spring.model.User;
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
public class WebUserController {

    private UserService userService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="userService") //Define the name of the called function. This allows us to have more than 1.
    public void setUserService(UserService ps){
        this.userService = ps;
    }

    @RequestMapping(value = "web/user", method = RequestMethod.GET)
    public String login(HttpSession session, Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        List<User> userList = this.userService.listUsers();
        model.addAttribute("users", userList);
        Admin admin = (Admin)session.getAttribute("admin");
        model.addAttribute("admin", admin);
        return "user";
    }

    @RequestMapping(value = "web/user/delete", method = RequestMethod.GET)
    public String delete(HttpSession session,
                         @RequestParam(value="user_id") int user_id,
                         Model model) {
        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(this.userService.getUserById(user_id) != null){
            this.userService.removeUser(user_id);
        }

        return "redirect:/web/user";
    }

    @RequestMapping(value = "web/user/add", method = RequestMethod.POST)
    public String add(HttpSession session,
                          @RequestParam(value="username") String username,
                          @RequestParam(value="password") String password,
                          @RequestParam(value="email") String email,
                         Model model) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(username != "" && password != "" && email != "") {

            List<User> userList = this.userService.isExist(email);
            if(userList == null || userList.isEmpty()){

                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //Create new instance of encoder for password.
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                User user = new User(username,
                        passwordEncoder.encode(password),
                        String.valueOf(UUID.randomUUID()), //Format the data back to string type to store it as a string.
                        email,
                        dateFormat.format(new Date()));

                this.userService.addUser(user); //Add the user to the DB
                return "redirect:/web/user";
            }
            else{
                model.addAttribute("error", "This email has been used");
                return "404"; //Inform user of existing registration.
            }
        }
        else {
            model.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }

    @RequestMapping(value = "web/user/update", method = RequestMethod.POST)
    public String update(HttpSession session,
                         @RequestParam(value="user_id") int user_id,
                         @RequestParam(value="username") String username,
                         @RequestParam(value="password") String password,
                         @RequestParam(value="email") String email,
                      Model model) {

        if(session.getAttribute("admin") == null){
            return "redirect:/web/login";
        }

        if(username != "" && password != "" && email != "") {

            User user = this.userService.getUserById(user_id);
            if (user == null) {
                model.addAttribute("error", "No user found with this id");
                return "404"; // Verification Technique.
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //Create new instance of encoder for password.
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setUsername(username);
            this.userService.updateUser(user); //Add the user to the DB
            return "redirect:/web/user";

        }
        else {
            model.addAttribute("error", "The form input should not be empty");
            return "404"; // Verification Technique.
        }
    }


}
