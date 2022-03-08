package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.dto.CompanyMedicineDto;
import com.searchmedicine.demo.repositories.PharmacyMedicineRepository;
import java.util.List;
import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PharmacyMedicineService {
  private final PharmacyMedicineRepository pharmacyMedicineRepository;


}
