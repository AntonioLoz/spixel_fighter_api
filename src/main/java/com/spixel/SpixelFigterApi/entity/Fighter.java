package com.spixel.SpixelFigterApi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fighter")
public class Fighter {

    @Id
    private Long id;
}
