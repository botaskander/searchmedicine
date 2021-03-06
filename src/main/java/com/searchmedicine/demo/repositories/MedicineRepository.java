package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public interface MedicineRepository extends JpaRepository<Medicine,Long> {
        List<Medicine> getAllByIsExchangeTrue();

    List<Medicine> findAllByOrderByAddedDateDesc();

    List<Medicine> findAllByOrderByViewAmountAscSearchAmountAsc();

    List<Medicine> findTop10ByOrderByViewAmountDesc();

    List<Medicine> findTop10ByOrderBySearchAmountDesc();

    List<Medicine> findAllByFarmGroupId(Long id);
}
