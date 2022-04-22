package com.searchmedicine.demo.services;

import com.searchmedicine.demo.dto.UserMedicineDto;
import com.searchmedicine.demo.entities.Image;
import com.searchmedicine.demo.entities.ImagesUserMedicine;
import com.searchmedicine.demo.entities.UserMedicine;
import org.h2.engine.User;

import java.util.List;

public interface UserMedicineService {
    UserMedicine addMedicine(UserMedicine userMedicine);
    void deleteUserMedicine(UserMedicine userMedicine);
    ImagesUserMedicine addImage(ImagesUserMedicine imagesUserMedicine);
    UserMedicine getUserMedicine(Long id);
    UserMedicineDto getUserMedicineDetail(Long id);
    List<UserMedicine> getAllUserMedicine();
    List<ImagesUserMedicine> getImageList(Long id);
    ImagesUserMedicine getImage(Long id);
    List<UserMedicine> getUserMedicineByUser(Long id);
    UserMedicine editUserMedicine(UserMedicine userMedicine);
    void deleteUserMedicineImages(ImagesUserMedicine imagesUserMedicine);
    void deleteUserMedicineImagesAll(Long id);
}
