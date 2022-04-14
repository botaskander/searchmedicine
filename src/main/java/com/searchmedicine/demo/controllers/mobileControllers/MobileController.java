package com.searchmedicine.demo.controllers.mobileControllers;

import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.dto.CompanyMedicineDto;
import com.searchmedicine.demo.services.MedicineService;
import com.searchmedicine.demo.services.PharmacyMedicineService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "api/chang/mobile")
public class MobileController {

  private final PharmacyMedicineService pharmacyMedicineService;

  @Autowired
  MedicineService medicineService;

  @GetMapping()
  public ResponseEntity<?> getAllCompanyMedicine() {
    System.out.println(medicineService.getAllMedicine());
    System.out.println("heli");
    return new ResponseEntity<>(medicineService.getAllMedicine(), HttpStatus.OK);
  }

  @GetMapping("/medicine/{id}")
  public List<PharmacyMedicine> getAllPharmacyCompany(@PathVariable Long id){
    System.out.println("******************");
    pharmacyMedicineService.sendNotification(pharmacyMedicineService.getAllPharmacyMedicine(id).get(0));
    return pharmacyMedicineService.getAllPharmacyMedicine(id);
  }
}
