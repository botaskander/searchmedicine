package com.searchmedicine.demo.controllers;

import com.searchmedicine.demo.entities.ImagesUserMedicine;
import com.searchmedicine.demo.entities.UserMedicine;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.payload.UploadFileResponse;
import com.searchmedicine.demo.services.FileStorageService;
import com.searchmedicine.demo.services.UserMedicineService;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "api/img")
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    UserMedicineService userMedicineService;

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
    @PostMapping(path="/editImage", consumes = {"multipart/form-data"})
    public List<UploadFileResponse> editImage(@RequestParam("files") MultipartFile[] files,@RequestParam(required=false,name="id") Object id) throws IOException {
        String stringToConvert = String.valueOf(id);
        Long convertedLong = Long.parseLong(stringToConvert);
        UserMedicine userMedicine= userMedicineService.getUserMedicine(convertedLong);
        System.out.println(userMedicine);
        List<UploadFileResponse> listFile =Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());

        long date=System.currentTimeMillis();
        Date addeddate=new Date(date);
        userMedicine.setUrlImage(listFile.get(0).getFileName());
        System.out.println("////////////error//////////////");
        UserMedicine userMedicine1=userMedicineService.editUserMedicine(userMedicine);
        System.out.println("****************error****************");
        List<ImagesUserMedicine> imagesUserMedicines =new ArrayList<>();
        imagesUserMedicines.addAll(userMedicineService.getImageList(userMedicine.getId()));
        String filePath="C:\\Users\\????????\\IdeaProjects\\diploma\\downloadImages\\images\\";
        for(int i=0;i<imagesUserMedicines.size();i++){
            String imagesPath=filePath+imagesUserMedicines.get(i).getUrl();
            Files.delete( Paths.get(imagesPath));
            System.out.println("File "
                    + imagesPath
                    + " successfully removed");
        }
        userMedicineService.deleteUserMedicineImagesAll(userMedicine.getId());
        System.out.println("/////////////////////"+userMedicine.getId());
        for(int i=0;i<listFile.size();i++){
            ImagesUserMedicine imagesUserMedicine=new ImagesUserMedicine();
            System.out.println(listFile.get(i).getFileName());
            imagesUserMedicine.setUrl(listFile.get(i).getFileName());
            imagesUserMedicine.setUserMedicine(userMedicine);
            imagesUserMedicine.setAddedDate(addeddate);
            userMedicineService.addImage(imagesUserMedicine);
        }
        return  listFile;
    }

    @PostMapping(path="/uploadMultipleFiles", consumes = {"multipart/form-data"})
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,@RequestParam(required=false,name="id") Object id) {
        String stringToConvert = String.valueOf(id);
        Long convertedLong = Long.parseLong(stringToConvert);
        UserMedicine userMedicine= userMedicineService.getUserMedicine(convertedLong);
        List<UploadFileResponse> listFile =Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());

        long date=System.currentTimeMillis();
        Date addeddate=new Date(date);
        userMedicine.setUrlImage(listFile.get(0).getFileName());
        UserMedicine userMedicine1=userMedicineService.editUserMedicine(userMedicine);
        System.out.println(userMedicine);
        for(int i=0;i<listFile.size();i++){

            ImagesUserMedicine imagesUserMedicine=new ImagesUserMedicine();
            System.out.println(listFile.get(i).getFileName());
            imagesUserMedicine.setUrl(listFile.get(i).getFileName());
            imagesUserMedicine.setUserMedicine(userMedicine);
            imagesUserMedicine.setAddedDate(addeddate);
            userMedicineService.addImage(imagesUserMedicine);
        }

        return  listFile;
    }

    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
    @GetMapping(
            value = "/get-image-with-media-type/{fileName:.+}"
    )
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable String fileName) throws IOException {
        System.out.println(fileName);
        Resource resource = fileStorageService.loadFileAsResource(fileName);
//        resource.getFile().getAbsolutePath();
//        InputStream in = getClass()
//                .getResourceAsStream(resource.getFile().getAbsolutePath());
        InputStream in=resource.getInputStream();
        System.out.println(IOUtils.toByteArray(in));
        return IOUtils.toByteArray(in);

    }
    private Users getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            return (Users) authentication.getPrincipal();
        }
        return null;
    }


}
