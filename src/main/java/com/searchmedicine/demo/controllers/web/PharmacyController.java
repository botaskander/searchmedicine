package com.searchmedicine.demo.controllers.web;

import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;
import com.searchmedicine.demo.entities.views.PharmacyMedicineView;
import com.searchmedicine.demo.entities.views.Response;
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
    public Response deletePharmacyMedicines(@RequestParam("pharmacyId") Long pharmacyId,
                                            @RequestParam("pharmacyMedicineId") Long pharmacyMedicineId) {
        return webPharmacyService.deletePharmacyMedicine(pharmacyId,pharmacyMedicineId);
    }

    @GetMapping("/medicines/get-one/{id}")
    public PharmacyMedicineView getPharmacyMedicineDetailed(@PathVariable("id") Long id){
        return webPharmacyService.getDetailedPharmacyMedicine(id);
    }
}
