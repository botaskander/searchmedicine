package com.searchmedicine.demo.controllers.web;

import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.dto.Address;
import com.searchmedicine.demo.dto.PharmacyDTO;
import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;
import com.searchmedicine.demo.entities.views.PharmacyMedicineView;
import com.searchmedicine.demo.entities.views.Response;
import com.searchmedicine.demo.services.PharmacyMedicineService;
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

import java.util.List;

import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/web/pharmacy")
@PreAuthorize("hasRole('ROLE_PHARMACY')")
public class PharmacyController {

    private final WebPharmacyService webPharmacyService;
    private final PharmacyMedicineService pharmacyMedicineService;

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

    @PostMapping("/medicines/save")
    public Response savePharmacyMedicine(@RequestBody PharmacyMedicine pharmacyMedicine){
        return webPharmacyService.savePharmacyMedicine(pharmacyMedicine);
    }

    @DeleteMapping("/medicines/delete")
    public com.searchmedicine.demo.entities.views.Response deletePharmacyMedicines(@RequestParam("pharmacyId") Long pharmacyId,
                                                                                   @RequestParam("pharmacyMedicineId") Long pharmacyMedicineId) {
        return webPharmacyService.deletePharmacyMedicine(pharmacyId,pharmacyMedicineId);
    }

    @GetMapping("/medicines/get-one/{id}")
    public PharmacyMedicineView getPharmacyMedicineDetailed(@PathVariable("id") Long id){
        return webPharmacyService.getDetailedPharmacyMedicine(id);
    }

    @GetMapping("/get-profile-info")
    public Pharmacy getPharmacyProfileInfo(){
        Users users=getUser();
        System.out.println(webPharmacyService.getPharmacyByUserId(users.getId()));
        return webPharmacyService.getPharmacyByUserId(users.getId());
    }

    @PostMapping("/getLonglat")
    public ResponseEntity<?> getLonglat(@RequestBody Address address){
        System.out.println(address);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/checkNotification")
    public ResponseEntity<?> checkNotification(){
        Long id =1l;
        PharmacyMedicine pharmacyMedicine=pharmacyMedicineService.getPharmacyMedicineById(id);
        pharmacyMedicineService.sendNotification(pharmacyMedicine);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/edit-profile")
    public Response editProfile(@RequestBody PharmacyDTO pharmacyDTO){
            Pharmacy pharmacy=webPharmacyService.getPharmacy(pharmacyDTO.getPharmacyId());
            pharmacy.setName(pharmacyDTO.getName());
            pharmacy.setWhatsappNumber(pharmacyDTO.getWhatsappNumber());
            pharmacy.setOffPhone(pharmacyDTO.getOffPhone());
            pharmacy.setPhoneNumber(pharmacyDTO.getPhoneNumber());
            LocalDateTime lt = LocalDateTime.now();
            pharmacy.setLastUpdateDate(lt);
            pharmacy.setWorkStartTime(LocalTime.parse(pharmacyDTO.getWorkStartTime()));
            pharmacy.setWorkEndTime(LocalTime.parse(pharmacyDTO.getWorkEndTime()));
            return webPharmacyService.saveProfile(pharmacy);
    }

    @PutMapping("/edit-address")
    public Response  editAddress(@RequestBody Address address){
        return webPharmacyService.saveAddress(address);
    }

    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }

}
