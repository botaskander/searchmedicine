package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.PharmacyMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PharmacyMedicineRepository extends JpaRepository<PharmacyMedicine,Long> {
    List<PharmacyMedicine> findAllByMedicineId(Long id);

    List<PharmacyMedicine> findAllByPharmacyIdOrderByAddedDateDesc(Long id);
    List<PharmacyMedicine> findAllByMedicine_Id(Long id);


}

