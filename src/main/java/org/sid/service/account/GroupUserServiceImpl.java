package org.sid.service.account;

import lombok.NoArgsConstructor;
import org.sid.dao.GroupeRepository;
import org.sid.entities.Groupe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@NoArgsConstructor
public class GroupUserServiceImpl implements GroupUserService{
    @Autowired
    private GroupeRepository groupeRepository;
    @Override
    public void addUserToGroup() {

    }

    @Override
    public List<Groupe> getAllGroup() {
        return groupeRepository.findAll();
    }

    @Override
    public void saveGroup(Groupe g) {
        this.groupeRepository.save(g);
    }
}
