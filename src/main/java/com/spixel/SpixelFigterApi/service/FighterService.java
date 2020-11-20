package com.spixel.SpixelFigterApi.service;

import com.spixel.SpixelFigterApi.repository.FighterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//TODO

@Service
public class FighterService {

    @Autowired
    private FighterRepository repository;
}
