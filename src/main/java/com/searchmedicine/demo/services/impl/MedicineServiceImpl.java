package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.entities.CompanyMedicine;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.repositories.MedicineRepository;
import com.searchmedicine.demo.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;


}
