package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.RegisterRequests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRequestRepository extends JpaRepository<RegisterRequests,Long> {
}
