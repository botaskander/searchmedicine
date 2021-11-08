package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.Address;
import com.searchmedicine.demo.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Repository
public interface CityRepository extends JpaRepository<City,Long>  {
}
