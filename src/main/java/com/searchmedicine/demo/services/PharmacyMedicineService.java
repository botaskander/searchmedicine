package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.repositories.PharmacyMedicineRepository;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PharmacyMedicineService {
  private final PharmacyMedicineRepository pharmacyMedicineRepository;

  public List<PharmacyMedicine> getAllPharmacyMedicine(Long id){
    return  pharmacyMedicineRepository.findAllByCompanyMedicine_Id(id);
  }


}
