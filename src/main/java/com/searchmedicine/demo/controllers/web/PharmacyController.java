package com.searchmedicine.demo.controllers.web;

import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.views.AdminHomeInfo;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;
import com.searchmedicine.demo.services.UserService;
import com.searchmedicine.demo.services.WebPharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/web/pharmacy")
public class PharmacyController {

    private final WebPharmacyService webPharmacyService;
    private final UserService userService;
    @GetMapping("/users/get-home-info")
    public PharmacyHomeInfo getHomeInfo(){
        return  webPharmacyService.getPharmacyHomeInfo(userService.getUser("talshynkb@gmail.com"));
    }


}
