package org.sid.web.email;

import freemarker.template.TemplateException;
import org.sid.entities.AppUser;
import org.sid.service.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EmailController {
    @Autowired
    private MailService notificationService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public String ActivationMail(AppUser user){

        Map<String,Object> model =new HashMap<>();
        model.put("username",user.getUsername());
        try {
            notificationService.sendEmailWithAttachment(user,"email-template.ftl",model);
        } catch (MailException mailException) {
            System.out.println(mailException);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Congratulations! Your mail has been send to the user.";
    }

    public String ConfirmMail(AppUser user, String password){
        Map<String,Object> model =new HashMap<>();
        model.put("username",user.getUsername());
        model.put("password",password);
        try {
            notificationService.sendEmailWithAttachment(user,"EmailConfirm.ftl",model);
        } catch (MailException mailException) {
            System.out.println(mailException);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Congratulations! Your mail has been send to the user.";
    }
    public String ConfirmMailiNFO(AppUser user, String Message){
        Map<String,Object> model =new HashMap<>();
        model.put("username",user.getUsername());
        model.put("message",Message);
        try {
            notificationService.sendEmailWithAttachment(user,"email-Info.ftl",model);
        } catch (MailException mailException) {
            System.out.println(mailException);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Congratulations! Your mail has been send to the user.";
    }

    public String ResetPasswordMail(AppUser user,String token){
        String url="http://localhost:4200//resetPassword?token="+token;
        Map<String,Object> model =new HashMap<>();
        model.put("username",user.getUsername());
        model.put("link",url);
        try {
            notificationService.sendEmailWithAttachment(user,"ResetPassword.ftl",model);
        } catch (MailException mailException) {
            System.out.println(mailException);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Congratulations! Your mail has been send to the user.";
    }
    public String InfoEmail(AppUser user){

        Map<String,Object> model =new HashMap<>();
        model.put("username",user.getUsername());
        try {
            notificationService.sendEmailWithAttachment(user,"ActivedAccount.ftl",model);
        } catch (MailException mailException) {
            System.out.println(mailException);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return "Congratulations! Your mail has been send to the user.";
    }


}

