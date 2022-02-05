package com.searchmedicine.demo.controllers;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/chang")
public class MainRestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/special")
    public  String Special(){
        System.out.println("////////hello////////////");
        return "hello";}
}


