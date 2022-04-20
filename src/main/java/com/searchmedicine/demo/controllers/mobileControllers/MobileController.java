package com.searchmedicine.demo.controllers.mobileControllers;

import com.searchmedicine.demo.dto.ListReserverRequestDto;
import com.searchmedicine.demo.entities.Response;
import com.searchmedicine.demo.services.ListReserverService;
import com.searchmedicine.demo.services.ListWaiterService;
import com.searchmedicine.demo.services.MedicineService;
import com.searchmedicine.demo.services.PharmacyMedicineService;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "api/chang/mobile")
public class MobileController {

  private final PharmacyMedicineService pharmacyMedicineService;
  private final MedicineService medicineService;
  private final ListWaiterService listWaiterService;
  private final ListReserverService listReserverService;

  @Autowired
  MedicineService medicineService;

  private final AdminService adminService;

  @GetMapping("")
  public ResponseEntity<?> getAllCompanyMedicine() {
    return new ResponseEntity<>(medicineService.getAllMedicine(), HttpStatus.OK);
  }

  @GetMapping("/medicine/{id}")
  public ResponseEntity<?>getAllPharmacyCompany(@PathVariable Long id,
      @RequestParam("type") Optional<String> type,
      @RequestParam("isSortAsc") Optional<Boolean> isSortAsc){

    String filterType = type.orElse("all");
    Boolean isAsc = isSortAsc.orElse(false);

    return new ResponseEntity<>(pharmacyMedicineService.getAllPharmacyUserMedicine(id,filterType,isAsc),HttpStatus.OK);
  }

  @GetMapping("/medicine/pharmacy/{id}")
  public ResponseEntity<?> getPharmacyCompany(@PathVariable Long id){
    return new ResponseEntity<>(pharmacyMedicineService.getPharmacyMedicine(id),HttpStatus.OK);
  }
  @GetMapping("/notification/{id}")
  public Response sendNotification(@PathVariable Long id){
    return listWaiterService.saveListWaiter(id);
  }

  @PostMapping("/book")
  public Response sendBook(@RequestBody ListReserverRequestDto listReserverRequestDto){
    return listReserverService.saveListReserver(listReserverRequestDto);
  }
  @GetMapping("/medicine/top-by-views")
  public ResponseEntity<?> getMedicinesTopView(){
    return new ResponseEntity<>(medicineService.getMedicineTopView(), HttpStatus.OK);
  }

  @GetMapping("/medicine/top-by-search")
  public ResponseEntity<?> getMedicinesTopSearch(){
   return new ResponseEntity<>(medicineService.getMedicineTopSearch(), HttpStatus.OK);
  }

  @GetMapping("/medicine/top-by-reservation")
  public ResponseEntity<?> getMedicinesTopReserved(){
    return new ResponseEntity<>(medicineService.getMedicineTopReserved(),HttpStatus.OK);
  }

  @GetMapping("/medicines/farm-group/{id}")
  public ResponseEntity<?> getMedicinesByFarmGroup(@PathVariable Long id){
    return new ResponseEntity<>(medicineService.getMedicineByFarmGroup(id),HttpStatus.OK);
  }

  @GetMapping("/farm-groups/get-all")
  public ResponseEntity<?> getFarmGroup(){
    System.out.println(adminService.getAllFarmGroups());
    return new ResponseEntity<>(adminService.getAllFarmGroups(),HttpStatus.OK);
  }
}
