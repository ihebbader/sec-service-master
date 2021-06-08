package org.sid.dao;

import org.sid.entities.Values;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ValuesRepository extends JpaRepository<Values,Long> {
}
