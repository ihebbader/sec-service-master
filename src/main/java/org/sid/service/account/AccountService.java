package org.sid.service.account;

import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import java.util.List;

public interface AccountService {
    void saveUser(AppUser user);
    void save(AppRole role);
    AppUser loadUserByUsername(String username);
    void addRoleToUser(String username, String rolename);
    AppUser existUserByUsername(String username);
    AppUser getUserByUsername(String username);
    String DeleteUser(AppUser user);
    void createPasswordResetForUser(AppUser user, String token);
    String validatePasswordResetToken(String token);
    List<AppUser> findAllUserwithRoles();
    List<AppUser> findUserByUsernameLike( String username);
    List<AppUser> findUserByEmailLike( String email);
}
