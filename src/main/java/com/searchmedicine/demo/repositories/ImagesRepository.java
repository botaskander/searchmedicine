package com.searchmedicine.demo.repositories;

import com.searchmedicine.demo.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ImagesRepository extends JpaRepository<Image,Long> {

    @Query(value = "SELECT * FROM images_medicine WHERE medicine_id=?",nativeQuery = true)
    List<Image> findByMedicineId(Long id);

    @Modifying
    @Query(value = "DELETE FROM images_medicine  WHERE medicine_id = ?",
            nativeQuery = true)
    void deleteImagesById (Long id);
}
