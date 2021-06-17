package org.sid.web.account;
import org.sid.dao.GroupeRepository;
import org.sid.entities.AppUser;
import org.sid.entities.Groupe;
import org.sid.service.account.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class GroupController {
@Autowired
    private GroupUserService groupUserService;
@Autowired
private GroupeRepository groupeRepository;
@GetMapping("/getAllGroup")
public List<Groupe> getAllGroupOfUser(){
    return groupUserService.getAllGroup();
}
@PostMapping("/addGroup")
    public void addGroup(@RequestBody Groupe g){
    Date d =new Date();
    g.setDateDeCreation(d);
    g.getAppUsers().forEach(user->{
        user.setMaxGroup(user.getMaxGroup()+1);
    });
    this.groupUserService.saveGroup(g);
}
@PostMapping("/deleteUserFromGroup/{id}")
    public void deleteUserFromGroupe(@RequestBody AppUser user , @PathVariable Long id){
    Groupe groupe=groupeRepository.getOne(id);
    groupe.getAppUsers().remove(user);
    groupeRepository.save(groupe);
}
@DeleteMapping("/deleteGroup/{id}")
    public void deleteGroupe(@PathVariable Long id ){
    Groupe g = groupeRepository.getOne(id);
    groupeRepository.delete(g);
}
}
