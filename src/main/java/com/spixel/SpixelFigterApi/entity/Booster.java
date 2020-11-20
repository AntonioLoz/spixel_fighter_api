package com.spixel.SpixelFigterApi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "boosters")
public class Booster {
    @Id
    private Long id;
}
