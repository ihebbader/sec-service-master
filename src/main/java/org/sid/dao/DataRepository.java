package org.sid.dao;

import org.sid.entities.DataValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DataRepository extends JpaRepository<DataValues,Long> {
}
