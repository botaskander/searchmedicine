package com.searchmedicine.demo.repositories;


import com.searchmedicine.demo.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface AddressRepository extends JpaRepository<Address,Long>  {
    Optional<Address> findByNameAndLatitudeAndLongitude(String name, double latitude,double longitude );
}
