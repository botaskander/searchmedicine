package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.ImagesUserMedicine;
import com.searchmedicine.demo.entities.UserMedicine;

import java.util.List;

public interface UserMedicineService {
    UserMedicine addMedicine(UserMedicine userMedicine);
    ImagesUserMedicine addImage(ImagesUserMedicine imagesUserMedicine);
    UserMedicine getUserMedicine(Long id);
    List<UserMedicine> getAllUserMedicine();
    List<ImagesUserMedicine> getImageList(Long id);
    ImagesUserMedicine getImage(Long id);

}
