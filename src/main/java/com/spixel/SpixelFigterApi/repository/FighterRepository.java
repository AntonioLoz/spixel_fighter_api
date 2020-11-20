package com.spixel.SpixelFigterApi.repository;

import com.spixel.SpixelFigterApi.entity.Fighter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FighterRepository extends JpaRepository<Fighter, Long> {
}
