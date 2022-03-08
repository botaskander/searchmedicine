package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PharmacyMedicineRepository extends JpaRepository<PharmacyMedicine,Long> {
    List<PharmacyMedicine> findAllByCompanyMedicine_Id(Long id);
}

