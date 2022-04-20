package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.entities.ImagesUserMedicine;
import com.searchmedicine.demo.entities.UserMedicine;
import com.searchmedicine.demo.repositories.ImagesUserMedicineRepository;
import com.searchmedicine.demo.repositories.UserMedicineRepository;
import com.searchmedicine.demo.services.UserMedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMedicineServiceImpl implements UserMedicineService {
    @Autowired
    private UserMedicineRepository userMedicineRepository;

    @Autowired
    private ImagesUserMedicineRepository imagesUserMedicineRepository;

    @Override
    public UserMedicine addMedicine(UserMedicine userMedicine) {
        return userMedicineRepository.save(userMedicine);
    }

    @Override
    public void deleteUserMedicine(UserMedicine userMedicine) {
         userMedicineRepository.delete(userMedicine);
    }


    @Override
    public ImagesUserMedicine addImage(ImagesUserMedicine imagesUserMedicine) {
        return imagesUserMedicineRepository.save(imagesUserMedicine);
    }

    @Override
    public UserMedicine getUserMedicine(Long id) {
        return userMedicineRepository.findById(id).get();
    }

    @Override
    public List<UserMedicine> getAllUserMedicine() {
        return userMedicineRepository.findAll();
    }

    @Override
    public List<ImagesUserMedicine> getImageList(Long id) {
        return imagesUserMedicineRepository.findAllByUserMedicineId(id);
    }

    @Override
    public ImagesUserMedicine getImage(Long id) {
        return imagesUserMedicineRepository.findByUserMedicineId(id);
    }

    @Override
    public List<UserMedicine> getUserMedicineByUser(Long id) {
        return userMedicineRepository.findAllByUserId(id);
    }

    @Override
    public UserMedicine editUserMedicine(UserMedicine userMedicine) {
        return userMedicineRepository.save(userMedicine);
    }

    @Override
    public void deleteUserMedicineImages(ImagesUserMedicine imagesUserMedicine) {
        imagesUserMedicineRepository.delete(imagesUserMedicine);
    }

    @Override
    public void deleteUserMedicineImagesAll(Long id ) {
        imagesUserMedicineRepository.deleteImagesById(id);
    }


}
