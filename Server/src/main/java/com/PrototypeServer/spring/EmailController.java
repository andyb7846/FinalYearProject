package com.PrototypeServer.spring;

import com.PrototypeServer.spring.model.ErrorResponse;
import com.PrototypeServer.spring.model.SuccessResponse;
import com.PrototypeServer.spring.model.User;
import com.PrototypeServer.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

@RestController
public class EmailController {

    private UserService userService;

    @Autowired(required=true) //This means the program doesn't have to call this function, it's done automatically.
    @Qualifier(value="userService") //Define the name of the called function. This allows us to have more than 1.
    public void setUserService(UserService ps){
        this.userService = ps;
    }

    @RequestMapping(value = "/email/forgot", method = RequestMethod.POST)
    public Object create(@RequestParam(value="email") String email, Model model){

        List<User> userList = this.userService.isExist(email);
        if (userList == null || userList.isEmpty()) {
            return new ErrorResponse(3, "the email is not registered");
        }

        User user = userList.get(0);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = getAlphaNumericString(10);
        user.setPassword(passwordEncoder.encode(password));

        this.userService.updateUser(user);

        String USER_NAME = "andyb7846";  // GMail user name (just the part before "@gmail.com")
        String PASSWORD = "HelloWorld123"; // GMail password
        String RECIPIENT = email;

        String from = USER_NAME;
        String pass = PASSWORD;
        String[] to = { RECIPIENT }; // list of recipient email addresses
        String subject = "Reset Password";
        String body = password;

        //sendFromGMail(from, pass, to, subject, body);

        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            return new ErrorResponse(2, "succeed");
        }
        catch (AddressException ae) {
            ae.printStackTrace();
            return new ErrorResponse(2, ae.toString());
        }
        catch (MessagingException me) {
            me.printStackTrace();
            return new ErrorResponse(2, me.toString());
        }
    }

    public String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}
