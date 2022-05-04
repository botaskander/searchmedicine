package com.searchmedicine.demo.controllers;
import com.searchmedicine.demo.dto.MedicineExchange;
import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.payload.UploadFileResponse;
import com.searchmedicine.demo.services.FileStorageService;
import com.searchmedicine.demo.services.MedicineService;
//import com.searchmedicine.demo.services.PharmacyMedicineService;
import com.searchmedicine.demo.services.UserMedicineService;
import lombok.RequiredArgsConstructor;
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

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "api/chang")
public class MainRestController {
    @Autowired
    UserMedicineService userMedicineService;

    @Autowired
    MedicineService medicineService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/special")
    public  String Special(){
        System.out.println("////////hello////////////");
        return "hello";}


//    private final PharmacyMedicineService pharmacyMedicineService;


    @GetMapping("/farm")
    public ResponseEntity<?> getMiniFarms() {
        System.out.println("-----------------------Diana------------------------");
        System.out.println(medicineService.getAllMedicine());
        return new ResponseEntity<>(medicineService.getAllMedicine(), HttpStatus.OK);
    }
    @GetMapping("/listMedicinesAvailable")
    public ResponseEntity<?> getListMedicinesAvailable() {
        System.out.println("-----------------------list of medicines ------------------------");
        System.out.println(medicineService.getAllAvailableMedicine());
        return   new ResponseEntity<>(medicineService.getAllAvailableMedicine(), HttpStatus.OK);
    }
    @PostMapping("/addPostForExchange")
    public ResponseEntity<?> addPostForExchange(@RequestBody MedicineExchange medicineExchange) {
        System.out.println("-----------------------adding Medicines ------------------------");
        Medicine medicine = medicineService.getMedicine(Long.parseLong(medicineExchange.getIdMedicine()));
        UserMedicine userMedicine= new UserMedicine();
        long date=System.currentTimeMillis();
        Date addeddate=new Date(date);
        userMedicine.setDescription(medicineExchange.getDescription());
        userMedicine.setMedicine(medicine);

        LocalDate localDate = LocalDate.parse(convertDate(medicineExchange.getMonth(), medicineExchange.getYear()));
        LocalDateTime localDateTime1 = localDate.atStartOfDay();
        userMedicine.setExpDate(localDateTime1);
        userMedicine.setPhone(medicineExchange.getPhone());
        userMedicine.setAddedDate(addeddate);
        Users user = getUser();
        userMedicine.setUser(user);
        Address address = new Address();
        address.setLatitude(medicineExchange.getLatitude());
        address.setLongitude(medicineExchange.getLongitude());
        address.setName(medicineExchange.getAddressName());
        Address address1=userMedicineService.addAddress(address);
        userMedicine.setAddress(address1);

        UserMedicine newUserMedicine= userMedicineService.addMedicine(userMedicine);
        return   new ResponseEntity<>( newUserMedicine, HttpStatus.OK);
    }

    private String convertDate(String month,String year){
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("MMMM")
                .withLocale(Locale.ENGLISH);
        TemporalAccessor accessor = parser.parse(month);
        int monthInt=accessor.get(ChronoField.MONTH_OF_YEAR);
        System.out.println(monthInt);
        String monthString=String.valueOf(monthInt);
        if(monthString.length()==1){
            monthString="0"+String.valueOf(monthInt);
        }
        String date=year+"-"+monthString+"-01";
        return  date;

    }

    @GetMapping("/listExchangeUser")
    public ResponseEntity<?> getListExchangeUser() {
        System.out.println("-----------------------list of exchange  ------------------------");
        Users user = getUser();
        List<UserMedicine> userMedicine= userMedicineService.getUserMedicineByUser(user.getId());

        return   new ResponseEntity<>( userMedicine, HttpStatus.OK);
    }
    @GetMapping("/listImageUser/{id}")
    public ResponseEntity<?> getListImageUser(@PathVariable Long id) {
        System.out.println("-----------------------list of images  ------------------------"+id);
        List<ImagesUserMedicine> imagesUserMedicines= new ArrayList<>();
        imagesUserMedicines.addAll( userMedicineService.getImageList(id));
        return   new ResponseEntity<>( imagesUserMedicines, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUserMedicine/{id}")
    public ResponseEntity<?> deleteUserMedicine(@PathVariable Long id){
        try {
            System.out.println(id);
            System.out.println("helo//////////////// delete function");
            UserMedicine userMedicine =userMedicineService.getUserMedicine(id);
            List<ImagesUserMedicine> imagesUserMedicines=userMedicineService.getImageList(id);

            String filePath="C:\\Users\\бота\\IdeaProjects\\diploma\\downloadImages\\images\\";
            for(int i=0;i<imagesUserMedicines.size();i++){
                String imagesPath=filePath+imagesUserMedicines.get(i).getUrl();
                Files.delete( Paths.get(imagesPath));
                System.out.println("File "
                        + imagesPath
                        + " successfully removed");
            }
            userMedicineService.deleteUserMedicine(userMedicine);
            return  ResponseEntity.ok("Entity deleted");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/editExchangeMedicine")
    public ResponseEntity<?> editExchangeMedicine(@RequestBody MedicineExchange medicineExchange) {
        System.out.println("-----------------------editing Medicines ------------------------");
        Medicine medicine = medicineService.getMedicine(Long.parseLong(medicineExchange.getIdMedicine()));
        UserMedicine userMedicine= userMedicineService.getUserMedicine(medicineExchange.getId());
        System.out.println(userMedicine);
        userMedicine.setDescription(medicineExchange.getDescription());
        userMedicine.setMedicine(medicine);
        LocalDate localDate = LocalDate.parse(convertDate(medicineExchange.getMonth(), medicineExchange.getYear()));
        LocalDateTime localDateTime1 = localDate.atStartOfDay();
        userMedicine.setExpDate(localDateTime1);
        userMedicine.setPhone(medicineExchange.getPhone());
        Address address = new Address();
        address.setLatitude(medicineExchange.getLatitude());
        address.setLongitude(medicineExchange.getLongitude());
        address.setName(medicineExchange.getAddressName());
        Address address1=userMedicineService.addAddress(address);
        userMedicine.setAddress(address1);
        UserMedicine newUserMedicine= userMedicineService.editUserMedicine(userMedicine);
        return   new ResponseEntity<>( newUserMedicine, HttpStatus.OK);
    }


    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }


}


