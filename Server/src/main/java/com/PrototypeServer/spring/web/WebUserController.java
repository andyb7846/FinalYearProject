package com.PrototypeServer.spring.web;

import com.PrototypeServer.spring.model.Admin;
import com.PrototypeServer.spring.model.User;
import com.PrototypeServer.spring.service.UserService;
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
}
