package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.FarmGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface FarmGroupRepository extends JpaRepository<FarmGroup,Long> {
}
