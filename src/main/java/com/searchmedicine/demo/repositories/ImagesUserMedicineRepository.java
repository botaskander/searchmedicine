package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.ImagesUserMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ImagesUserMedicineRepository extends JpaRepository<ImagesUserMedicine,Long> {
}
