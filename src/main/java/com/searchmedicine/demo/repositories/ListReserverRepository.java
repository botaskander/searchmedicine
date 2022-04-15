package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.ListReserver;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
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
  List<ListReserver> findAllByPharmacyId(Long id);

}
