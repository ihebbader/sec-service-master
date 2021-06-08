package org.sid.service.account;

import org.sid.entities.Groupe;

import java.util.List;

public interface GroupUserService {
    public void addUserToGroup();
    public List<Groupe> getAllGroup();
    public void saveGroup(Groupe g);
}
