package com.searchmedicine.demo.controllers.web;

import com.searchmedicine.demo.entities.*;
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
    public Response saveFarmGroup(@RequestBody FarmGroup farmGroup){
        return  adminService.saveFarmGroup(farmGroup);
    }

    @DeleteMapping("/farm-groups/delete/{id}")
    public Response deleteFarmGroup(@PathVariable("id") Long id) {
        return adminService.deleteFarmGroup(id);
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
    public Response saveMedicine(@RequestBody Medicine medicine){
        return adminService.saveMedicine(medicine);
    }

    @DeleteMapping("/medicines/delete/{id}")
    public Response deleteMedicine(@PathVariable("id") Long id) {
        return adminService.deleteMedicine(id);
    }

    @GetMapping("/medicines/get-by-id/{id}")
    public ResponseEntity<?> getMedicine(@PathVariable("id") Long id){
        return  ResponseEntity.ok(adminService.getMedicine(id));
    }

    @GetMapping("/countries/get-all")
    public ResponseEntity<?> getAllCountries(){
        return  ResponseEntity.ok(adminService.getAllCountries());
    }

    @PostMapping("/countries/save")
    public Response saveCountry(@RequestBody Country country){
        return adminService.saveCountry(country);
    }

    @DeleteMapping("/countries/delete/{id}")
    public Response deleteCountry(@PathVariable("id") Long id) {
        return adminService.deleteCountry(id);
    }

    @GetMapping("/countries/get-by-id/{id}")
    public ResponseEntity<?> getCountry(@PathVariable("id") Long id){
        return  ResponseEntity.ok(adminService.getCountry(id));
    }

    @GetMapping("/cities/get-all")
    public ResponseEntity<?> getAllCities(){
        return  ResponseEntity.ok(adminService.getAllCities());
    }

    @PostMapping("/cities/save")
    public Response saveCity(@RequestBody City city){
        return adminService.saveCity(city);
    }

    @DeleteMapping("/cities/delete/{id}")
    public Response deleteCity(@PathVariable("id") Long id) {
        return adminService.deleteCity(id);
    }

    @GetMapping("/cities/get-by-id/{id}")
    public ResponseEntity<?> getCity(@PathVariable("id") Long id){
        return  ResponseEntity.ok(adminService.getCity(id));
    }

    @GetMapping("/regions/get-by-id/{id}")
    public ResponseEntity<?> getRegion(@PathVariable("id") Long id){
        return  ResponseEntity.ok(adminService.getRegion(id));
    }

    @GetMapping("/regions/get-all")
    public ResponseEntity<?> getAllRegions(){
        return  ResponseEntity.ok(adminService.getAllRegions());
    }

    @PostMapping("/regions/save")
    public Response saveRegion(@RequestBody Region region){
        return adminService.saveRegion(region);
    }

    @DeleteMapping("/regions/delete/{id}")
    public Response deleteRegion(@PathVariable("id") Long id) {
        return adminService.deleteRegion(id);
    }

}
