package org.sid.dao;

import org.sid.entities.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

}
