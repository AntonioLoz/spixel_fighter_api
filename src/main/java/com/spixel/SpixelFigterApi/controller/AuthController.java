package com.spixel.SpixelFigterApi.controller;

import com.spixel.SpixelFigterApi.entity.*;
import com.spixel.SpixelFigterApi.models.*;
import com.spixel.SpixelFigterApi.repository.RoleRepository;
import com.spixel.SpixelFigterApi.repository.UserRepository;
import com.spixel.SpixelFigterApi.security.JwtUtils;
import com.spixel.SpixelFigterApi.service.RoleService;
import com.spixel.SpixelFigterApi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) throws Exception {


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );

        System.out.println("TEST[AuthController]: " + loginRequest.toString());

        String jwt = jwtUtils.generateJwttokent(authentication);

        UserDetaisImp userDetais = (UserDetaisImp) authentication.getPrincipal();
        List<String> roles = userDetais.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetais.getId(), userDetais.getUsername(), userDetais.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) throws RoleNotFoundException {
        if (userService.existByUsername(signupRequest.getUsername())) {

            return ResponseEntity
                    .badRequest()
                    .body( new MessageResponse("Username is already in use!"));
        }

        if (userService.existByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Email is already in use!"));
        }


        UserEntity user = new UserEntity(signupRequest.getEmail(), signupRequest.getUsername(), signupRequest.getFirstname(),
                signupRequest.getLastname(), signupRequest.getBirthday(), (long) 0, 0, signupRequest.getGender(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        // TODO Terminar de implementar la autorizacion decidiendo los roles que vamos a necesitar

        if (strRoles == null) {
            Role userRole = roleService.findByName(ERole.ROLE_USER);
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = null;
                        try {
                            adminRole = roleService.findByName(ERole.ROLE_ADMIN);
                        } catch (RoleNotFoundException e) {
                            e.printStackTrace();
                        }
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = null;
                        try {
                            modRole = roleService.findByName(ERole.ROLE_MODERATOR);
                        } catch (RoleNotFoundException e) {
                            e.printStackTrace();
                        }
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = null;
                        try {
                            userRole = roleService.findByName(ERole.ROLE_USER);
                        } catch (RoleNotFoundException e) {
                            e.printStackTrace();
                        }
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userService.createUserOrUpdate(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        }
        catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        }
        catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
