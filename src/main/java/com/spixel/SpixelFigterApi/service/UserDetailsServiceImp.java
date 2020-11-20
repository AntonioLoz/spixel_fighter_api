package com.spixel.SpixelFigterApi.service;

import com.spixel.SpixelFigterApi.exception.UserNotFoundException;
import com.spixel.SpixelFigterApi.models.UserDetaisImp;
import com.spixel.SpixelFigterApi.entity.UserEntity;
import com.spixel.SpixelFigterApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 *
 * Haremos esta implementacion de UserDetailsService ya que necesitamos cargar el usuario por medio del email.
 *
 */

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return UserDetaisImp.build(user);
    }

    @Transactional
    public UserDetails loadUserByEmail(String email) throws UserNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        return UserDetaisImp.build(user);
    }

}
