package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.dto.CompanyMedicineDto;
import com.searchmedicine.demo.entities.dto.PharmacyMedicineDto;
import com.searchmedicine.demo.repositories.PharmacyMedicineRepository;
import java.util.List;
import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PharmacyMedicineService {

  private final PharmacyMedicineRepository pharmacyMedicineRepository;
  private final EntityManager em;

  public List<PharmacyMedicineDto> getAllCompanyPharmacy(Long id) {

    List<PharmacyMedicineDto> pharmacyCompany = em.createQuery(" select "
                + " new com.searchmedicine.demo.entities.dto.PharmacyMedicineDto ("
                + " phc.id,ph.name, m.name, c.name, cm.imageUrl,phc.price )"
                + " from Medicine m,Company c,Pharmacy ph,PharmacyMedicine phc  "
                + " inner join CompanyMedicine cm on phc.companyMedicine.id = cm.id "
                + " where cm.medicine.id = m.id and cm.company.id = c.id "
                + "and phc.pharmacy.id = ph.id and phc.companyMedicine.id =" + id,
            PharmacyMedicineDto.class)
        .getResultList();

    return pharmacyCompany;

  }
}
