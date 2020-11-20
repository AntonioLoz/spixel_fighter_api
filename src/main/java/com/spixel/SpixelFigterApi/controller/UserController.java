package com.spixel.SpixelFigterApi.controller;

import com.spixel.SpixelFigterApi.entity.UserEntity;
import com.spixel.SpixelFigterApi.exception.UserNotFoundException;
import com.spixel.SpixelFigterApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return new ResponseEntity<List<UserEntity>>(service.getUsers(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUse(@PathVariable("id") Integer id) throws UserNotFoundException {
        return new ResponseEntity<UserEntity>(service.getUserById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserEntity> createOrUpdateUser(@RequestBody UserEntity user) {
        return new ResponseEntity<UserEntity>(service.createUserOrUpdate(user), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus removeUserById(@PathVariable("id") Integer id) throws UserNotFoundException {
        service.deleteUserById(id);
        return HttpStatus.FORBIDDEN;
    }
}
