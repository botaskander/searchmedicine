package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByRole(String name);

}
