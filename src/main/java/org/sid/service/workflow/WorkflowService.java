package org.sid.service.workflow;
import org.sid.entities.Property;

import java.util.List;
import java.util.Optional;


public interface WorkflowService {
    void save(Property p);
    Optional<Property> getById(Long id);
    List<Property> getAllProperty();

}
