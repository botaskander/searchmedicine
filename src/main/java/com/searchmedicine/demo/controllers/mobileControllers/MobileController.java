package com.searchmedicine.demo.controllers.mobileControllers;

import com.searchmedicine.demo.dto.ListReserverRequestDto;
import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.entities.ListWaiter;
import com.searchmedicine.demo.entities.Response;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.services.*;

import com.searchmedicine.demo.services.UserMedicineService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
  private final AdminService adminService;
  private final UserMedicineService userMedicineService;

  @GetMapping("")
  public ResponseEntity<?> getAllCompanyMedicine() {
    return new ResponseEntity<>(medicineService.getAllMedicine(), HttpStatus.OK);
  }

  @GetMapping("/medicine/{id}")
  public ResponseEntity<?>getAllPharmacyCompany(@PathVariable Long id,
      @RequestParam("type") Optional<String> type,
      @RequestParam("isSortAsc") Optional<Boolean> isSortAsc){

    String filterType = type.orElse("Все");
    Boolean isAsc = isSortAsc.orElse(false);
    System.out.println("Diana salem");
    return new ResponseEntity<>(pharmacyMedicineService.getAllPharmacyUserMedicine(id,filterType,isAsc),HttpStatus.OK);
  }

  @GetMapping("/medicine/pharmacy/{id}")
  public ResponseEntity<?> getPharmacyCompany(@PathVariable Long id){
    return new ResponseEntity<>(pharmacyMedicineService.getPharmacyMedicine(id),HttpStatus.OK);
  }

  @GetMapping("/medicine/user/{id}")
  public ResponseEntity<?> getUserMedicine(@PathVariable Long id){
    return new ResponseEntity<>(userMedicineService.getUserMedicineDetail(id),HttpStatus.OK);
  }
  @GetMapping("/medicine/image/{id}")
  public ResponseEntity<?> getUserImageUser(@PathVariable Long id){
    return new ResponseEntity<>(userMedicineService.getUserMedicineDetail(id),HttpStatus.OK);
  }


  @PostMapping("/notification")
  public Response sendNotification(@RequestBody Long id){
    Users users=getUser();
    return listWaiterService.saveListWaiter(id,users);
  }

  @PostMapping("/book")
  public Response sendBook(@RequestBody ListReserverRequestDto listReserverRequestDto){
    Users users=getUser();
    return listReserverService.saveListReserver(listReserverRequestDto,users);
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

  @GetMapping("/reservation/get-by-user")
  public ResponseEntity<?> getReservationByUser(){
    Users user = getUser();
    System.out.println(listReserverService.getReservationByUser(user.getId()));
    return new ResponseEntity<>(listReserverService.getReservationByUser(user.getId()),HttpStatus.OK);
  }
  @GetMapping("/notification/get-by-user")
  public ResponseEntity<?> getNotificationByUser(){
    Users user = getUser();
    System.out.println(listWaiterService.getWaiterByUserId(user.getId()));
    return new ResponseEntity<>(listWaiterService.getWaiterByUserId(user.getId()),HttpStatus.OK);
  }

  @DeleteMapping("/reservation/delete/{id}")
  public ResponseEntity<?> deleteReservation(@PathVariable Long id){
    ListReserver listReserver=adminService.getListReserver(id);
    listReserver.setIsDeleted(true);
    listReserverService.save(listReserver);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  @DeleteMapping("/notification/delete/{id}")
  public ResponseEntity<?> deleteNotification(@PathVariable Long id){
    ListWaiter listWaiter=adminService.getListWaiter(id);
    listWaiter.setIsDeleted(true);
    listWaiterService.save(listWaiter);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  private Users getUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(!(authentication instanceof AnonymousAuthenticationToken)){
      return (Users) authentication.getPrincipal();
    }
    return null;
  }
}
