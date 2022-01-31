package com.searchmedicine.demo.controllers.mobileControllers;

import com.searchmedicine.demo.entities.CompanyMedicine;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.dto.PharmacyMedicineDto;
import com.searchmedicine.demo.services.CompanyMedicineService;
import com.searchmedicine.demo.services.PharmacyMedicineService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "api/change/mobile")
public class MobileController {

  private final PharmacyMedicineService pharmacyMedicineService;

  @GetMapping()
  public List<PharmacyMedicineDto> getMiniFarms() {
    return pharmacyMedicineService.getAllPharmacyMedicine();
  }

}
