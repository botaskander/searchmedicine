package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.ListWaiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface ListWaiterRepository extends JpaRepository<ListWaiter,Long> {
}
