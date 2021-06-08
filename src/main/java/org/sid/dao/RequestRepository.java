package org.sid.dao;

import org.sid.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RequestRepository extends JpaRepository< Request,Long> {
}
