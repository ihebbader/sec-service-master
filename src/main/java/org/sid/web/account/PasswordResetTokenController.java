package org.sid.web.account;

import lombok.Data;
import org.sid.Exception.ExpiredTokenException;
import org.sid.Exception.TokenNotFoundException;
import org.sid.dao.AppUserRepository;
import org.sid.dao.PasswordResetTokenRepository;
import org.sid.entities.AppUser;
import org.sid.entities.PasswordResetToken;
import org.sid.service.account.AccountService;
import org.sid.web.email.EmailController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PasswordResetTokenController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private EmailController emailController;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/resetPassword")
    public void resetPassword(@RequestBody String username){
        AppUser user = accountService.loadUserByUsername(username);
        System.out.println(user);
        if(user == null ) throw new UsernameNotFoundException("utilisateur n'existe pas");
        String token = UUID.randomUUID().toString();
        System.out.println(token);
        accountService.createPasswordResetForUser(user,token);
        emailController.ResetPasswordMail(user,token);
    }
    @PostMapping("/changePassword")
    public AppUser changePassword(@RequestBody changePasswordForm  changePasswordForm) throws TokenNotFoundException, ExpiredTokenException {
        PasswordResetToken passwordResetToken= passwordResetTokenRepository.findByToken(changePasswordForm.getToken());
        if (passwordResetToken == null) throw new TokenNotFoundException("Token not found");
        AppUser user= passwordResetToken.getUser();
        System.out.println(changePasswordForm.getPassword());
        System.out.println(changePasswordForm.getToken());
        user.setPassword(bCryptPasswordEncoder.encode(changePasswordForm.getPassword()));
               System.out.println(passwordResetToken);
               passwordResetTokenRepository.deleteById(passwordResetToken.getId());
               appUserRepository.save(user);
        return user;
    }
}
@Data
class changePasswordForm{
    private String Token;
    private String password;
}
