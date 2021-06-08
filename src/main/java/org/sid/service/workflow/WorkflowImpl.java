package org.sid.service.workflow;

import lombok.NoArgsConstructor;
import org.sid.dao.PropertyRepository;
import org.sid.entities.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@NoArgsConstructor
public class WorkflowImpl implements WorkflowService {
    @Autowired
    private PropertyRepository propertyRepository ;
    @Override
    public void save(Property p) {
        this.propertyRepository.save(p);
    }

    @Override
    public Optional<Property> getById(Long id) {
        return propertyRepository.findById(id);
    }

    @Override
    public List<Property> getAllProperty() {
        return propertyRepository.findAll();
    }
}
