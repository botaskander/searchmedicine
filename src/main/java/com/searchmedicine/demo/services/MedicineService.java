package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.Medicine;

import java.util.List;

public interface MedicineService {

    List<Medicine> getAllAviableMedicine();

    Medicine getMedicine(Long id);

    List<Medicine> getAllMedicine();
}
