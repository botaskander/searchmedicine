package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.PharmacyMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface PharmacyMedicineRepository extends JpaRepository<PharmacyMedicine,Long> {
    List<PharmacyMedicine> findAllByMedicineId(Long id);

    @Query("SELECT pm from PharmacyMedicine pm where pm.pharmacy.id=:id and pm.isArc=false ORDER BY pm.addedDate desc ")
    List<PharmacyMedicine> findAllByPharmacyId(@Param("id") Long id);
    List<PharmacyMedicine> findAllByMedicine_Id(Long id);


}

