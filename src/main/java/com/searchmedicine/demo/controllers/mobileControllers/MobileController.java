package com.searchmedicine.demo.controllers.mobileControllers;

import com.searchmedicine.demo.dto.ListReserverRequestDto;
import com.searchmedicine.demo.entities.Response;
import com.searchmedicine.demo.services.ListReserverService;
import com.searchmedicine.demo.services.ListWaiterService;
import com.searchmedicine.demo.services.MedicineService;
import com.searchmedicine.demo.services.PharmacyMedicineService;

import com.searchmedicine.demo.services.UserMedicineService;
import java.util.Optional;
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
  private final ListReserverService listReserverService;
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


  @GetMapping("/notification/{id}")
  public Response sendNotification(@PathVariable Long id){
    return listWaiterService.saveListWaiter(id);
  }

  @PostMapping("/book")
  public Response sendBook(@RequestBody ListReserverRequestDto listReserverRequestDto){
    return listReserverService.saveListReserver(listReserverRequestDto);
  }
}
