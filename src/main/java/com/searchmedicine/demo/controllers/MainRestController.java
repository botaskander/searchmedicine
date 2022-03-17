package com.searchmedicine.demo.controllers;
import com.searchmedicine.demo.dto.CompanyMedicineDto;
import com.searchmedicine.demo.dto.MedicineExchange;
import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.payload.UploadFileResponse;
import com.searchmedicine.demo.repositories.UserMedicineRepository;
import com.searchmedicine.demo.services.CompanyMedicineService;
import com.searchmedicine.demo.services.FileStorageService;
import com.searchmedicine.demo.services.PharmacyMedicineService;
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

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/chang")
@RequiredArgsConstructor
public class MainRestController {
    @Autowired
    UserMedicineService userMedicineService;

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

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


    private final PharmacyMedicineService pharmacyMedicineService;
    private final CompanyMedicineService companyMedicineService;

    @GetMapping("/farm")
    public List<CompanyMedicineDto> getMiniFarms() {
        System.out.println("-----------------------Diana------------------------");
        System.out.println(companyMedicineService.getAllCompanyMedicine());
        return companyMedicineService.getAllCompanyMedicine();
    }
    @GetMapping("/listMedicinesAvailable")
    public List<CompanyMedicineDto> getListMedicinesAvailable() {
        System.out.println("-----------------------list of medicines ------------------------");
        System.out.println(companyMedicineService.getAllCompanyMedicine());
        return companyMedicineService.getAllCompanyMedicine();
    }
    @PostMapping("/addPostForExchange")
    public ResponseEntity<?> addPostForExchange(@RequestBody MedicineExchange medicineExchange) {
        System.out.println("-----------------------adding Medicines ------------------------");
        CompanyMedicine companyMedicine = companyMedicineService.findById(Long.parseLong(medicineExchange.getIdMedicine()));
        UserMedicine userMedicine= new UserMedicine();
        long date=System.currentTimeMillis();
        Date addeddate=new Date(date);
        userMedicine.setDescription(medicineExchange.getDescription());
        userMedicine.setCompanyMedicine(companyMedicine);

        LocalDate localDate = LocalDate.parse(convertDate(medicineExchange.getMonth(), medicineExchange.getYear()));
        LocalDateTime localDateTime1 = localDate.atStartOfDay();
        userMedicine.setExpDate(localDateTime1);
        userMedicine.setPhone(medicineExchange.getPhone());
        userMedicine.setAddedDate(addeddate);
        Users user = getUser();
        userMedicine.setUser(user);
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
    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }
    @GetMapping("/listExchangeUser")
    public ResponseEntity<?> getListExchangeUser() {
        System.out.println("-----------------------list of exchange  ------------------------");
        List<UserMedicine> userMedicine= userMedicineService.getAllUserMedicine();
        System.out.println(userMedicine);
        for(int i=0;i<userMedicine.size();i++){
            List<ImagesUserMedicine> imagesUserMedicines=userMedicineService.getImageList(userMedicine.get(i).getId());
            System.out.println(userMedicine.get(i).getId());
            userMedicine.get(i).setUrlImage(imagesUserMedicines.get(0).getUrl());

        }
        return   new ResponseEntity<>( userMedicine, HttpStatus.OK);
    }


}


