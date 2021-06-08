package org.sid.service.account;

import lombok.NoArgsConstructor;
import org.sid.dao.AppRoleRepository;
import org.sid.dao.AppUserRepository;
import org.sid.dao.PasswordResetTokenRepository;
import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.entities.PasswordResetToken;
import org.sid.service.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@NoArgsConstructor
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;

    @Override
    public void saveUser(AppUser user) {
        appUserRepository.save(user);

    }

    @Override
    public void save(AppRole role) {
         appRoleRepository.save(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        AppUser appUser=appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findByRoleName(rolename);
        appUser.getRoles().add(appRole);
    }

    @Override

    public AppUser existUserByUsername( String username) {
        return  appUserRepository.findByUsername(username);

        }

    @Override
    public AppUser getUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public String DeleteUser(AppUser user) {
        appUserRepository.delete(user);
        return "utilisateur supprimer avec succés";
    }
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    public void createPasswordResetForUser(AppUser user, String token){
        Date d=new Date(new Date().getTime()+28800000);
        PasswordResetToken passwordResetToken = new PasswordResetToken(token,user,d);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;    }

    @Override
    public List<AppUser> findAllUserwithRoles() {
        List<AppUser> list= appUserRepository.findAll();
        return list;
    }

    @Override
    public List<AppUser> findUserByUsernameLike(String username) {
        return appUserRepository.findUserByUsernameLike(username);
    }

    @Override
    public List<AppUser> findUserByEmailLike(String email) {
        return this.appUserRepository.findUserByEmailLike(email);
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getDateExpiration().before(cal.getTime());
    }
}
