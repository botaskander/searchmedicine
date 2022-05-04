package com.searchmedicine.demo.controllers.web;

import com.searchmedicine.demo.controllers.FileController;
import com.searchmedicine.demo.dto.ResponseDTO;
import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.entities.views.AdminHomeInfo;
import com.searchmedicine.demo.entities.views.Response;
import com.searchmedicine.demo.payload.UploadFileResponse;
import com.searchmedicine.demo.services.AdminService;
import com.searchmedicine.demo.services.FileStorageService;
import com.searchmedicine.demo.services.MedicineService;
import com.searchmedicine.demo.services.UserMedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/web/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private final MedicineService medicineService;
    @Autowired
    UserMedicineService userMedicineService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/medicines/test")
    public void test(){
        log.info("We're in test method");
    }

    @GetMapping("/check")
    public boolean access() {
        return true;
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
    public ResponseDTO saveMedicine(@RequestBody Medicine medicine){
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
    @PostMapping(path="/uploadMultipleFiles", consumes = {"multipart/form-data"})
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam(name="files") MultipartFile[] files, @RequestParam(required=false,name="id") Object id) {
        String stringToConvert = String.valueOf(id);
        Long convertedLong = Long.parseLong(stringToConvert);
        Medicine medicine= medicineService.getMedicine(convertedLong);
        List<UploadFileResponse> listFile = Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());

        long date=System.currentTimeMillis();
        Date addeddate=new Date(date);
        medicine.setUrl(listFile.get(0).getFileName());
        Medicine medicine1=medicineService.saveMedicine(medicine);
        System.out.println(medicine1);
        for(int i=0;i<listFile.size();i++){
            Image image=new Image();
            System.out.println(listFile.get(i).getFileName());
            image.setUrl(listFile.get(i).getFileName());
            image.setMedicine(medicine1);
            image.setAddedDate(addeddate);
            medicineService.addImage(image);
        }

        return  listFile;
    }
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file,getUser().getId());

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();


        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }
    @PostMapping(path="/editImage", consumes = {"multipart/form-data"})
    public List<UploadFileResponse> editImage(@RequestParam("files") MultipartFile[] files,@RequestParam(required=false,name="id") Object id) throws IOException {
        String stringToConvert = String.valueOf(id);
        Long convertedLong = Long.parseLong(stringToConvert);
        List<Image> images =new ArrayList<>();
        Medicine medicine=adminService.getMedicine(convertedLong);
        String filePath="C:\\Users\\бота\\IdeaProjects\\diploma\\downloadImages\\images\\";
        images.addAll(medicineService.getImageList(medicine.getId()));
        for(int i=0;i<images.size();i++){
            String imagesPath=filePath+images.get(i).getUrl();
            Files.delete( Paths.get(imagesPath));
            System.out.println("File "
                    + imagesPath
                    + " successfully removed");
        }
        System.out.println(medicine);
        List<UploadFileResponse> listFile =Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());

        long date=System.currentTimeMillis();
        Date addeddate=new Date(date);
        System.out.println("****************error****************");
        System.out.println(images.size());
        medicine.setUrl(listFile.get(0).getFileName());
        System.out.println("////////////error//////////////");
        Medicine medicine1=medicineService.saveMedicine(medicine);
        medicineService.deleteImagesAll(medicine.getId());
        for(int i=0;i<listFile.size();i++){
            Image image=new Image();
            System.out.println(listFile.get(i).getFileName());
            image.setUrl(listFile.get(i).getFileName());
            image.setMedicine(medicine1);
            image.setAddedDate(addeddate);
            medicineService.addImage(image);
        }
        return  listFile;
    }
    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }
    @GetMapping("/list-medicine/image/{id}")
    public ResponseEntity<?> getListImageMedicine(@PathVariable Long id) {

        System.out.println("-----------------------list of images  ------------------------"+id);
        List<Image> images= new ArrayList<>();
        images.addAll(medicineService.getImageList(id));
        System.out.println(images);
        return   new ResponseEntity<>( images, HttpStatus.OK);
    }

}
