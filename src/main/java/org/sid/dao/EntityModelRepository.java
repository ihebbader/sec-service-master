package org.sid.dao;

import org.sid.entities.EntityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EntityModelRepository extends JpaRepository<EntityModel,Long> {
}
