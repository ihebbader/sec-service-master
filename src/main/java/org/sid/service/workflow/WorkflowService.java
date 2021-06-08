package org.sid.service.workflow;
import org.sid.entities.Property;

import java.util.List;
import java.util.Optional;


public interface WorkflowService {
    public void save(Property p);
    public Optional<Property> getById(Long id);
    public List<Property> getAllProperty();

}
