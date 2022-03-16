package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.CompanyMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CompanyMedicineRepository extends JpaRepository<CompanyMedicine,Long> {

}