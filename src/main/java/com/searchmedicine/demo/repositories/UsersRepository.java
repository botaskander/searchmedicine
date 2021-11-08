package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);
    Boolean existsByEmail(String email);
}
