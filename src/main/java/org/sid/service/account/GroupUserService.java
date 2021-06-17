package org.sid.service.account;

import org.sid.entities.Groupe;

import java.util.List;

public interface GroupUserService {
    void addUserToGroup();
    List<Groupe> getAllGroup();
    void saveGroup(Groupe g);
}
