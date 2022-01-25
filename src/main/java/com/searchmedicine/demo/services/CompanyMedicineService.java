package com.searchmedicine.demo.services;

import com.searchmedicine.demo.repositories.CompanyMedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyMedicineService {
  private final CompanyMedicineRepository companyMedicineRepository;



}
