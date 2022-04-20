package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.UserMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface UserMedicineRepository  extends JpaRepository<UserMedicine,Long> {
    List<UserMedicine> findAllByUserId(Long id);
    List<UserMedicine> findAllByMedicine_Id(Long id);
}
