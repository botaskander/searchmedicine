package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.Roles;
import com.searchmedicine.demo.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  extends UserDetailsService  {
    boolean saveUser(Users user);
    Users editUser(Users user);
    Roles getRole(Long id);
    String confirmToken(String token);
}
