package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.ListReserver;

import java.util.List;
import java.util.Optional;

import com.searchmedicine.demo.entities.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ListReserverRepository extends JpaRepository<ListReserver, Long> {
    @Query("select lr "
            + " from ListReserver lr "
            + "where lr.isTook = false and lr.isExpired = false")
    List<ListReserver> getAllIsNotTokenAndIsNotExpired();

    @Query("SELECT lr "
            + "FROM ListReserver lr "
            + "WHERE lr.pharmacyMedicine.pharmacy.id = :pharmacyId " +
            "ORDER BY lr.reservedTime DESC ")
    List<ListReserver> findAllByPharmacy(@Param("pharmacyId") Long id);

    @Query("SELECT lr.pharmacyMedicine.medicine  from ListReserver lr  order by  count(lr.pharmacyMedicine.medicine) desc   ")
    List<Medicine> findTop();

    @Query("SELECT lr.pharmacyMedicine.medicine "
            + "FROM ListReserver lr "+
            "GROUP BY (lr.pharmacyMedicine.medicine.id)")
    List<Medicine> ff();

    List<ListReserver> findAllByUsersId(Long id);


}
