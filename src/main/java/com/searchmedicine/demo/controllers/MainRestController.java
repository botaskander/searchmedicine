package com.searchmedicine.demo.controllers;

import com.searchmedicine.demo.entities.CompanyMedicine;
import com.searchmedicine.demo.services.CompanyMedicineService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping(value = "api/change")
public class MainRestController {

  private final CompanyMedicineService companyMedicineService;

//  @GetMapping
//  public List<CompanyMedicine> getMiniFarms(
//      @RequestParam String sortField,
//      @RequestParam Boolean isSortAsc,
//      @RequestParam(defaultValue = "0") Integer page,
//      @RequestParam(defaultValue = "30") Integer pageSize) {
//
//    return CompanyMedicineService.getAllForAdmin(sortField,isSortAsc,page, pageSize);
//  }
}
