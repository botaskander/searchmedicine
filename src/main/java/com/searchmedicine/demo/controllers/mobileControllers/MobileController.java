package com.searchmedicine.demo.controllers.mobileControllers;

import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.Response;
import com.searchmedicine.demo.services.ListWaiterService;
import com.searchmedicine.demo.services.MedicineService;
import com.searchmedicine.demo.services.PharmacyMedicineService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "api/chang/mobile")
public class MobileController {

  private final PharmacyMedicineService pharmacyMedicineService;
  private final MedicineService medicineService;
  private final ListWaiterService listWaiterService;

  @GetMapping("")
  public ResponseEntity<?> getAllCompanyMedicine() {
    return new ResponseEntity<>(medicineService.getAllMedicine(), HttpStatus.OK);
  }

  @GetMapping("/medicine/{id}")
  public List<PharmacyMedicine> getAllPharmacyCompany(@PathVariable Long id){
    return pharmacyMedicineService.getAllPharmacyMedicine(id);
  }

  @GetMapping("/notification/{id}")
  public Response sendNotification(@PathVariable Long id){
    return listWaiterService.saveListWaiter(id);
  }
}
