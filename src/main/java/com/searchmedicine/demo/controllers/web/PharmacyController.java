package com.searchmedicine.demo.controllers.web;

import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.views.AdminHomeInfo;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;
import com.searchmedicine.demo.services.UserService;
import com.searchmedicine.demo.services.WebPharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/web/pharmacy")
@PreAuthorize("hasRole('ROLE_PHARMACY')")
public class PharmacyController {

    private final WebPharmacyService webPharmacyService;

    @GetMapping("/check")
    public boolean access() {
        return true;
    }

    @GetMapping("/users/get-home-info")
    public PharmacyHomeInfo getHomeInfo(@RequestParam("id") Long id){
        return  webPharmacyService.getPharmacyHomeInfo(id);
    }

    @GetMapping("/reserves")
    public List<ListReserver> getPharmacyReserves(@RequestParam("id") Long id){
        return webPharmacyService.getPharmacyReserves(id);
    }

    @GetMapping("/medicines")
    public List<PharmacyMedicine> getPharmacyMedicines(@RequestParam("id") Long id){
        return webPharmacyService.getPharmacyMedicines(id);
    }


}
