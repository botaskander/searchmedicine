package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.ListReserver;
import java.util.List;
import java.util.Optional;

import com.searchmedicine.demo.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ListReserverRepository extends JpaRepository<ListReserver,Long> {
  @Query("select lr "
      + " from ListReserver lr "
      + "where lr.isTook = false and lr.isExpired = false")
  List<ListReserver> getAllIsNotTokenAndIsNotExpired();

//  @Query("")
  List<ListReserver> findAllByPharmacyMedicine_Pharmacy_Id(Long id);

  List<ListReserver> findAllByUsersId(Long id);

  Optional<List<ListReserver>> findAllByPharmacyIdOrderByReservedTimeDesc(Long id);


}
