package com.searchmedicine.demo.controllers.web;

import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.entities.views.AdminHomeInfo;
import com.searchmedicine.demo.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public List<FarmGroup> getAllFarmGroups(){
        return adminService.getAllFarmGroups();
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
    public FarmGroup getFarmGroup(@PathVariable("id") Long id){
        return adminService.getFarmGroup(id);
    }

    @GetMapping("/medicines/get-all")
    public List<Medicine> getAllMedicines(){
        return  adminService.getAllMedicines();
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
    public Medicine getMedicine(@PathVariable("id") Long id){
        return adminService.getMedicine(id);
    }

    @GetMapping("/countries/get-all")
    public List<Country> getAllCountries(){
        return  adminService.getAllCountries();
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
    public Country getCountry(@PathVariable("id") Long id){
        return adminService.getCountry(id);
    }

    @GetMapping("/cities/get-all")
    public List<City> getAllCities(){
        return adminService.getAllCities();
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
    public City getCity(@PathVariable("id") Long id){
        return  adminService.getCity(id);
    }

    @GetMapping("/regions/get-by-id/{id}")
    public Region getRegion(@PathVariable("id") Long id){
        return  adminService.getRegion(id);
    }

    @GetMapping("/regions/get-all")
    public List<Region> getAllRegions(){
        return adminService.getAllRegions();
    }

    @PostMapping("/regions/save")
    public Response saveRegion(@RequestBody Region region){
        return adminService.saveRegion(region);
    }

    @DeleteMapping("/regions/delete/{id}")
    public Response deleteRegion(@PathVariable("id") Long id) {
        return adminService.deleteRegion(id);
    }

    @GetMapping("/companies/get-by-id/{id}")
    public Company getCompany(@PathVariable("id") Long id){
        return  adminService.getCompany(id);
    }

    @GetMapping("/companies/get-all")
    public List<Company> getAllCompanies(){
        return  adminService.getAllCompanies();
    }

    @PostMapping("/companies/save")
    public Response saveCompany(@RequestBody Company company){
        return adminService.saveCompany(company);
    }

    @DeleteMapping("/companies/delete/{id}")
    public Response deleteCompany(@PathVariable("id") Long id) {
        return adminService.deleteCompany(id);
    }

    @GetMapping("/users/get-by-id/{id}")
    public Users getUser(@PathVariable("id") Long id){
        return  adminService.getUser(id);
    }

    @GetMapping("/users/get-all")
    public List<Users> getAllUsers(@RequestParam(name = "roleCode",required = false, defaultValue = "") String roleCode){
        return  adminService.getAllUsers(roleCode);
    }

    @GetMapping("/users/get-home-info")
    public AdminHomeInfo getLastUsersInfo(){
        return  adminService.getAdminHomeUserInfo();
    }

}
