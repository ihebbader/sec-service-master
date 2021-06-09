package org.sid.web.account;

import lombok.Data;

import org.apache.logging.log4j.EventLogger;
import org.sid.Exception.UserAlreadyExistAuthenticationException;
import org.sid.dao.*;
import org.sid.entities.*;
import org.sid.service.account.AccountService;
import org.sid.service.workflow.DataModelService;
import org.sid.web.email.EmailController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailController emailController;
    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private UserNotificationRepository userNotificationRepository;
    @Autowired
    private RequestRepository requestRepository;


    @GetMapping("/getusers")
    public List<AppUser> test(){
        return this.accountService.findAllUserwithRoles();
    }
    @PostMapping("/register")
    public AppUser register(@RequestBody  AppUser userForm) throws UserAlreadyExistAuthenticationException {
        AppUser user = new AppUser();
        List<AppUser> appUsers = appUserRepository.findAll();
        List<AppUser> appUsers1= new ArrayList<>();
        appUsers.forEach(u->{
            u.getRoles().forEach(r->{
                if(r.getRoleName().equals("ADMIN")){
                    appUsers1.add(u);
                }
            });
        });

        Date date = new Date();
        System.out.println(userForm);
        if (accountService.loadUserByUsername(userForm.getUsername()) != null) throw new UserAlreadyExistAuthenticationException("cette utilisateur existe déja dans la base") ;
        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        user.setActived(false);
        UserNotification userNotification = new UserNotification();
        userNotification.setDateNotification(date);
        userNotification.setAppUsers(appUsers1);
        userNotification.setNotificationMessage("une nouvelle demande d'inscription a été ajouté");
        userNotificationRepository.save(userNotification);
        Request request = new Request();
        request.setAppUsers(user);
        request.setDateRequest(date);
        System.out.println(user);
          accountService.saveUser(user);
        requestRepository.save(request);
          accountService.addRoleToUser(user.getUsername(),"USER");
          emailController.ActivationMail(user);
          return user; 
    }

    @PostMapping("/adduser")
    public void addnewuser(@RequestBody AppUser user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        accountService.saveUser(user);
        accountService.addRoleToUser(user.getUsername(),"USER");
        System.out.println(user);

    }





    @PostMapping("/exist")
    public boolean exist(@RequestBody AppUser appUser){
        String username=appUser.getUsername();
        AppUser appUser1=accountService.loadUserByUsername(username);
        System.out.println(appUser1);
        return appUser1 != null;
    }
    @PostMapping ("/getuser")
    public AppUser getUserByUsername(@RequestBody String username){

        return appUserRepository.findByUsername(username) ;
    }
    @PostMapping("/addroles")
    public void AddRoles(@RequestBody AppUser user){
        this.accountService.addRoleToUser(user.getUsername(),"ADMIN");
    }
    @PostMapping("/addroles2")
    public void AddRoles2(@RequestBody AppUser user){
        System.out.println("ihebbader");
        this.accountService.addRoleToUser(user.getUsername(),"SUPERVISEUR");
    }

    @CrossOrigin(origins = "http://localhost:4200/**")
    @PostMapping("/delete")
    public void deletUser(@RequestBody AppUser user1) {
        System.out.println(user1);

        AppUser user = accountService.loadUserByUsername(user1.getUsername());
//        Set<AppRole> roles =new HashSet<AppRole>();
//        AppRole appRole =new AppRole();

        AppRole[] roles = user.getRoles().toArray(new AppRole[2]);
        for(int i=0;i<roles.length;i++){
            user.getRoles().remove(roles[i]);
        }
        System.out.println(roles);
        System.out.println(user);
        appUserRepository.save(user);
        accountService.DeleteUser(user);


    }
    @PutMapping("/updateUser")
    public AppUser updateuser(@RequestBody AppUser user) throws UsernameNotFoundException {
        AppUser user1= this.appUserRepository.save(user) ;
        return user1;
    }
    @PostMapping("/searchByUsername")
    public List<AppUser> findUserByUsernameLike (@RequestBody AppUser user){
        List<AppUser> list= accountService.findUserByUsernameLike(user.getUsername());

        return list;
    }
    @PostMapping("/searchByEmail")
    public List<AppUser> findUserByEmailLike (@RequestBody AppUser user){
        System.out.println(user);
        List<AppUser> list= accountService.findUserByEmailLike(user.getEmail());
        return list;
    }
    @GetMapping(path="/photoUser/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
        AppUser user=appUserRepository.findById(id).get();
        System.out.println(Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/Desktop/photo/"+user.getImage())));
        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+"/Desktop/photo/"+user.getImage()));
    }
    @PostMapping(path = "/uploadPhoto/{id}")
    public void uploadPhoto(MultipartFile file, @PathVariable Long id) throws Exception{
        System.out.println(id);
        AppUser user=appUserRepository.findById(id).get();
        user.setImage(file.getOriginalFilename());
        Files.write(Paths.get(System.getProperty("user.home")+"/Desktop/photo/"+user.getImage()),file.getBytes());
        appUserRepository.save(user);
    }
    @PostMapping(path = "/updateMotDePasse/{username}")
    public void changePass(@RequestBody AppUser user1,@PathVariable String username){

        AppUser user =appUserRepository.findByUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(user1.getPassword()));
        appUserRepository.save(user);
    }
    @PostMapping(path = "/verifyPass/{username}")
    public boolean verifyPass(@RequestBody AppUser user,@PathVariable String username){
        AppUser user1 =appUserRepository.findByUsername(username);
        System.out.println(user);
        if(bCryptPasswordEncoder.matches(user.getPassword(),user1.getPassword())){
            return false ;
        }else return true;
    }

    @GetMapping("/getDisabledAccount")
    public List<Request> getDisabledAccount(){
        List<Request> DisabledUser=requestRepository.findAll();

        System.out.println(DisabledUser);
        return DisabledUser;
    }
    @GetMapping("/getNotification")
    public List<UserNotification> getNotification(){

        List<UserNotification> userNotifications = userNotificationRepository.findAll();
        List<UserNotification> userNotifications1 = new ArrayList<>();
        if(userNotifications.size() !=0 ) {
            userNotifications.forEach(n -> {
                if (!n.isSended()) {
                    userNotifications1.add(n);
                }
            });
        }
        if (userNotifications1.size() != 0) {
        userNotifications1.forEach(not-> {
            not.setSended(true);
            userNotificationRepository.save(not);
        });
        }
        return userNotifications1;
    }
    @GetMapping("/getStatistic")
    public statistic getStatistic() {
        List<AppUser> appUsers = appUserRepository.findAll();
        statistic statistic = new statistic();
        appUsers.forEach(u -> {
            statistic.setNbreAllUser(statistic.getNbreAllUser() + 1);
            if (!u.isActived()) {
                statistic.setNbreDisabledAccount(statistic.getNbreDisabledAccount() + 1);
            }
            u.getRoles().forEach(r -> {
                if (r.getRoleName().equals("USER")) {
                    statistic.setNbreUser(statistic.getNbreUser() + 1);
                }
                if (r.getRoleName().equals("ADMIN")) {
                    statistic.setNbreAdmin(statistic.getNbreAdmin() + 1);
                }
            });
        });

        return statistic;
    }

    @GetMapping("/getAllNotification")
    public List<UserNotification> getAllNotification() {
        List<UserNotification> userNotifications = userNotificationRepository.findAll();
        return userNotifications;
    }
}

@Data
class UserForm{
    private String username;
    private String password;
    private String confirmedPassword;
    private String email;
}

@Data
class statistic{
    private int nbreAllUser=0;
    private int nbreAdmin=0;
    private int nbreUser=0;
    private int nbreDisabledAccount=0;

}