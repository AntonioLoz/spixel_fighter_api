package com.spixel.SpixelFigterApi.repository;

import com.spixel.SpixelFigterApi.models.ERole;
import com.spixel.SpixelFigterApi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r FROM Role r WHERE r.name = ?1")
    Optional<Role> findByName(ERole name) throws RoleNotFoundException;
}
