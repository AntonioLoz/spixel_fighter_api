package com.spixel.SpixelFigterApi.repository;

import com.spixel.SpixelFigterApi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUsername(String username);
    @Query("SELECT u FROM UserEntity u WHERE u.email = ?1")
    Optional<UserEntity> findByEmail(String email);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.username = ?1")
    Boolean existByUsername(String username);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE 'false' END FROM UserEntity u WHERE u.username = ?1")
    Boolean existByEmail(String email);
}
