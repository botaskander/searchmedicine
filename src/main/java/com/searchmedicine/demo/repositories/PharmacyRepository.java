package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy,Long> {

    List<Pharmacy> findAllByOrderByLastUpdateDateDesc();

//    Pharmacy getByUser_Id(Long id);

    Optional<Pharmacy> getByUser_Id(Long id);
}
