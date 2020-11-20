package com.spixel.SpixelFigterApi.service;

import com.spixel.SpixelFigterApi.entity.UserEntity;
import com.spixel.SpixelFigterApi.exception.UserNotFoundException;
import com.spixel.SpixelFigterApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * TODO mirar que tal el setear el password en create or update
 *
 */

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<UserEntity> getUsers() {
        return repository.findAll();
    }

    public UserEntity getUserById(Integer id) throws UserNotFoundException {
        return repository.findById(id).orElseThrow( () -> new UserNotFoundException("User not found with user id: " + id, id));
    }


    public UserEntity createUserOrUpdate(UserEntity user) {
        UserEntity userOut;
        if(repository.existByEmail(user.getEmail())){
            userOut = repository.findByUsername(user.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + user.getUsername()));
            userOut.setRoles(user.getRoles());
            userOut.setBirthday(user.getBirthday());
            userOut.setEmail(user.getEmail());
            userOut.setExp(user.getExp());
            userOut.setFirstName(user.getFirstName());
            userOut.setLastName(user.getLastName());
            userOut.setGender(user.getGender());
            userOut.setLevel(user.getLevel());
            userOut.setPassword(user.getPassword());
        }
        else {
            repository.save(user);
        }
        return user;
    }

    public void deleteUserById(Integer id) throws UserNotFoundException {
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
        else {
            throw new UserNotFoundException("User not found", id);
        }
    }

    public boolean existByUsername (String username) {
        return repository.existByUsername(username);
    }

    public boolean existByEmail(String email) {
        return repository.existByEmail(email);
    }
}
