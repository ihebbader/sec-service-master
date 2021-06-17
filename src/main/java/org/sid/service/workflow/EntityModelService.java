package org.sid.service.workflow;

import org.sid.entities.AppUser;
import org.sid.entities.EntityModel;

import java.util.List;

public interface EntityModelService {
    void SaveEntityModel(EntityModel e);
    void DeleteEntityFromModel(Long id);
    void addUserToEntityModel(Long id, List<AppUser> appUsers);
    void UpdateEntityStatus();
}
