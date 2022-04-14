package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.entities.UserMedicine;

import java.util.List;

public interface MedicineService {
    //Diana Kholidullayeva
    List<Medicine> getAllAviableMedicine();

    Medicine getMedicine(Long id);

    List<Medicine> getAllMedicine();
}
