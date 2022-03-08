package com.searchmedicine.demo.controllers.mobileControllers;

import com.searchmedicine.demo.entities.dto.CompanyMedicineDto;
import com.searchmedicine.demo.services.CompanyMedicineService;
import com.searchmedicine.demo.services.PharmacyMedicineService;

import java.io.Console;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "api/chang/mobile")
public class MobileController {

  private final PharmacyMedicineService pharmacyMedicineService;
  private final CompanyMedicineService companyMedicineService;

  @GetMapping()
  public List<CompanyMedicineDto> getMiniFarms() {
    System.out.println("-----------------------Diana------------------------");
    System.out.println(companyMedicineService.getAllCompanyMedicine());
    return companyMedicineService.getAllCompanyMedicine();
  }

}
