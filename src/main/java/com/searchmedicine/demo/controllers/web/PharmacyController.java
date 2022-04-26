package com.searchmedicine.demo.controllers.web;

import com.searchmedicine.demo.dto.Address;
import com.searchmedicine.demo.dto.ListReserverRequestDto;
import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.Response;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.views.AdminHomeInfo;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;
import com.searchmedicine.demo.services.UserService;
import com.searchmedicine.demo.services.WebPharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/web/pharmacy")
@PreAuthorize("hasRole('ROLE_PHARMACY')")
public class PharmacyController {

    private final WebPharmacyService webPharmacyService;
    private final UserService userService;

    @GetMapping("/check")
    public boolean access() {
        return true;
    }

    @GetMapping("/users/get-home-info")
    public PharmacyHomeInfo getHomeInfo(){
        return  webPharmacyService.getPharmacyHomeInfo(userService.getUser("talshynkb@gmail.com"));
    }

    @GetMapping("/get-profile-info")
    public Pharmacy getPharmacyProfileInfo(){
        Users users=getUser();
        System.out.println(webPharmacyService.getPharmacyByUserId(users.getId()));
        return webPharmacyService.getPharmacyByUserId(users.getId());
    }
    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }
    @PostMapping("/getLonglat")
    public ResponseEntity<?> getLonglat(@RequestBody Address address){
        System.out.println(address);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
