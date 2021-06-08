package org.sid.dao;

import org.sid.entities.AppUser;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface AppUserRepository extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
    @Query("SELECT u FROM AppUser u WHERE u.username LIKE CONCAT('%',:username,'%')")
    List<AppUser> findUserByUsernameLike(@Param("username") String username);
    @Query("SELECT u FROM AppUser u WHERE u.email LIKE CONCAT('%',:email,'%')")
    List<AppUser> findUserByEmailLike(@Param("email") String email);
//    @Query("SELECT u FROM AppUser u WHERE u.roles=':role'")
//    List<AppUser> finByRoles(@Param("role") String role );
}
