package org.sid.service.workflow;

import org.sid.entities.AppUser;
import org.sid.entities.EntityModel;

import java.util.List;

public interface EntityModelService {
    public void SaveEntityModel(EntityModel e);
    public void DeleteEntityFromModel(Long id);
    public void addUserToEntityModel(Long id , List<AppUser> appUsers);
    public void UpdateEntityStatus();
}
