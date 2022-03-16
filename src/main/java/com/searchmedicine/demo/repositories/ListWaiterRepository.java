package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.ListWaiter;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ListWaiterRepository extends JpaRepository<ListWaiter,Long> {

  @Query("select lw"
      + " from ListWaiter lw"
      + " where lw.isAppear = false and lw.medicine.id = ?1")
  List<ListWaiter> getNotificationItems(Long medicineId);
}

