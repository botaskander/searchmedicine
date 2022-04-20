package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.ImagesUserMedicine;
import com.searchmedicine.demo.entities.UserMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ImagesUserMedicineRepository extends JpaRepository<ImagesUserMedicine,Long> {
    ImagesUserMedicine findByUserMedicineId(Long id);
    List<ImagesUserMedicine>  findAllByUserMedicineId(Long id);

    @Modifying
    @Query(value = "DELETE FROM images_users_medicines  WHERE user_medicine_id = ?",
            nativeQuery = true)
    void deleteImagesById (Long id);
}
