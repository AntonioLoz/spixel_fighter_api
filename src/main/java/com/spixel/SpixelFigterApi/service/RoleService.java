package com.spixel.SpixelFigterApi.service;

import com.spixel.SpixelFigterApi.entity.Role;
import com.spixel.SpixelFigterApi.models.ERole;
import com.spixel.SpixelFigterApi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public Role findByName(ERole role) throws RoleNotFoundException {

        return repository.findByName(role).orElseThrow(() -> new RoleNotFoundException("Role not found"));
    }
}
