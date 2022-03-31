package com.searchmedicine.demo.controllers.web;

import com.searchmedicine.demo.entities.FarmGroup;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/web/admin")
public class AdminController {

    private final AdminService adminService;
    @GetMapping("/medicines/test")
    public void test(){
        log.info("We're in test method");
    }

    @GetMapping("/farm-groups/get-all")
    public ResponseEntity<?> getAllFarmGroups(){
        return  ResponseEntity.ok(adminService.getAllFarmGroups());
    }

    @PostMapping("/farm-groups/save")
    public ResponseEntity<?> saveFarmGroup(@RequestBody FarmGroup farmGroup){
        return  ResponseEntity.ok(adminService.saveFarmGroup(farmGroup));
    }

    @DeleteMapping("/farm-groups/delete/{id}")
    public ResponseEntity<?> deleteFarmGroup(@PathVariable("id") Long id) {
        return ResponseEntity.ok(adminService.deleteFartmGroup(id));
    }

    @GetMapping("/farm-groups/get-by-id/{id}")
    public ResponseEntity<?> getFarmGroup(@PathVariable("id") Long id){
        return  ResponseEntity.ok(adminService.getFarmGroup(id));
    }

    @GetMapping("/medicines/get-all")
    public ResponseEntity<?> getAllMedicines(){
        return  ResponseEntity.ok(adminService.getAllMedicines());
    }

    @PostMapping("/medicines/save")
    public ResponseEntity<?> saveMedicine(@RequestBody Medicine medicine){
        adminService.saveMedicine(medicine);
        return  ResponseEntity.ok().build();
    }

    @DeleteMapping("/medicines/delete/{id}")
    public ResponseEntity<?> deleteMedicine(@PathVariable("id") Long id) {
        adminService.deleteMedicine(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/medicines/get-by-id/{id}")
    public ResponseEntity<?> getMedicine(@PathVariable("id") Long id){
        return  ResponseEntity.ok(adminService.getMedicine(id));
    }



}
