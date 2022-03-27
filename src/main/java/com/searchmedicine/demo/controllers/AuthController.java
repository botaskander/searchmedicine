package com.searchmedicine.demo.controllers;


import com.searchmedicine.demo.dto.JwtRequest;
import com.searchmedicine.demo.dto.JwtResponse;
import com.searchmedicine.demo.dto.UserDTO;
import com.searchmedicine.demo.entities.Roles;
import com.searchmedicine.demo.entities.Users;

import com.searchmedicine.demo.jwt.JWTTokenGenerator;
import com.searchmedicine.demo.repositories.RolesRepository;
import com.searchmedicine.demo.repositories.UsersRepository;
import com.searchmedicine.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthController {
    @Autowired
    private JWTTokenGenerator jwtTokenGenerator;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value = "/auth")
    public ResponseEntity<?> auth(@RequestBody JwtRequest request) throws Exception{
        System.out.println("hello");

        Users user= userService.getUser(request.getEmail());
        if(user!=null){
            if (passwordEncoder.matches(request.getPassword(),user.getPassword())){
                authenticate(request.getEmail(), request.getPassword());
                final UserDetails userDetails =
                        userService.loadUserByUsername(request.getEmail());


                final String token = jwtTokenGenerator.generateToken(userDetails);

                System.out.println("//////"+"login"+"////");
                return ResponseEntity.ok(new JwtResponse(token));
            }
            else {
                System.out.println("/////////// password /////////////");
                return new ResponseEntity<>("password is not correct",HttpStatus.OK);
            }
        }
        else {
            System.out.println("/////////// email /////////////");
            return new ResponseEntity<>("email is not correct",HttpStatus.OK);
        }

    }

    @PostMapping("/authentication/register")
    public ResponseEntity<?> registerUser(@RequestBody Users request_user) throws Exception {
        Users user = new Users();
        user.setEmail(request_user.getEmail());
        user.setPassword(request_user.getPassword());
        user.setFullName(request_user.getFullName());
        if (!userService.saveUser(user)){
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/authentication/register/confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        userService.confirmToken(token);
        return ResponseEntity.ok().build();
    }

    public void authenticate(String email, String password) throws Exception{
        try{

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        }catch (DisabledException e){
            throw new DisabledException("User disabled. Please activate", e);
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid email or password", e);
        }

    }

    @GetMapping(value = "/profile")
    public ResponseEntity<?> profilePage(){
        Users user = getUser();
        return new ResponseEntity<>(new UserDTO(user.getId(), user.getFullName(), user.getEmail(), user.getRoles()), HttpStatus.OK);
    }

    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }

    @PostMapping(value = "/changeName")
    public ResponseEntity<?> changeName(@RequestBody Users request_user)throws Exception{
        System.out.println("hello");
        Users user = userService.getUser(request_user.getEmail());
        user.setFullName(request_user.getFullName());
        userService.editUser(user);
        return ResponseEntity.ok().build();
    }





}
