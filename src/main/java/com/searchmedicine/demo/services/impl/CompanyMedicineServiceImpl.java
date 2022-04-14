package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.dto.CompanyMedicineDto;
import com.searchmedicine.demo.repositories.CompanyMedicineRepository;
import com.searchmedicine.demo.services.CompanyMedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyMedicineServiceImpl implements CompanyMedicineService {
  private final CompanyMedicineRepository companyMedicineRepository;

  private final EntityManager em;

  public List<CompanyMedicineDto> getAllCompanyMedicine(){

    List<CompanyMedicineDto> pharmacyMedicine = em.createQuery(" select "
                    + " new com.searchmedicine.demo.dto.CompanyMedicineDto ("
                    + "cm.id, m.name,c.name, cm.imageUrl )"
                    + " from Medicine m, Company c  "
                    + " inner join CompanyMedicine cm on c.id = cm.company.id"
                    + " where  c.id = cm.company.id "
                    + " and m.id = cm.medicine.id", CompanyMedicineDto.class)
            .getResultList();

    return pharmacyMedicine;
  }

}
