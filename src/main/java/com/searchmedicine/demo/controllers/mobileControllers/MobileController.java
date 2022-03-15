package com.searchmedicine.demo.controllers.mobileControllers;

import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.dto.CompanyMedicineDto;
import com.searchmedicine.demo.services.CompanyMedicineService;
import com.searchmedicine.demo.services.PharmacyMedicineService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "api/chang/mobile")
public class MobileController {

  private final PharmacyMedicineService pharmacyMedicineService;
  private final CompanyMedicineService companyMedicineService;

  @GetMapping()
  public List<CompanyMedicineDto> getAllCompanyMedicine() {
    return companyMedicineService.getAllCompanyMedicine();
  }

  @GetMapping("/medicine/{id}")
  public List<PharmacyMedicine> getAllPharmacyCompany(@PathVariable Long id){
    System.out.println("******************");
    return pharmacyMedicineService.getAllPharmacyMedicine(id);
  }
}
