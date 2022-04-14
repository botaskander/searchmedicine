package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface MedicineRepository extends JpaRepository<Medicine,Long> {

    List<Medicine> findAllByOrderByAddedDateDesc();

    List<Medicine> findAllByOrderByViewAmountAscSearchAmountAsc();

}
