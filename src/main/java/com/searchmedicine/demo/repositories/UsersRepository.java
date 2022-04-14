package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE Users u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableAppUser(String email);

    List<Users> findAllByOrderByRegisterDateDesc();

}
