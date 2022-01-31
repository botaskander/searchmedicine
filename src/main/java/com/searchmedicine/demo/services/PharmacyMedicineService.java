package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.dto.PharmacyMedicineDto;
import com.searchmedicine.demo.repositories.PharmacyMedicineRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PharmacyMedicineService {
  private final PharmacyMedicineRepository pharmacyMedicineRepository;
  private final EntityManager em;

  public List<PharmacyMedicineDto> getAllPharmacyMedicine(){

    List<PharmacyMedicineDto> pharmacyMedicine = em.createQuery(" select "
            + " new com.searchmedicine.demo.entities.dto.PharmacyMedicineDto ("
            + " m,ph,c,pm.price, cm.imageUrl )"
            + " from Medicine m, Pharmacy ph,Company c  "
            + " inner join CompanyMedicine cm on c.id = cm.company.id"
            + " inner join PharmacyMedicine pm on cm.id = pm.companyMedicine.id "
            + " where  c.id = cm.company.id "
            + " and m.id = cm.medicine.id"
            + " and ph.id = pm.pharmacy.id" ,PharmacyMedicineDto.class)
        .getResultList();

    return pharmacyMedicine;
  }

}
