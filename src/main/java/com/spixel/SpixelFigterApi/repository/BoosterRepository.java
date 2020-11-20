package com.spixel.SpixelFigterApi.repository;

import com.spixel.SpixelFigterApi.entity.Booster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoosterRepository extends JpaRepository<Booster, Long> {
}
