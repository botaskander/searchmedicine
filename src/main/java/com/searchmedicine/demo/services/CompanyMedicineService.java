package com.searchmedicine.demo.services;

import com.searchmedicine.demo.dto.CompanyMedicineDto;

import java.util.List;

public interface CompanyMedicineService {
  List<CompanyMedicineDto> getAllCompanyMedicine();
import com.searchmedicine.demo.entities.CompanyMedicine;
import com.searchmedicine.demo.repositories.CompanyMedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyMedicineService {
  private final CompanyMedicineRepository companyMedicineRepository;

  private final EntityManager em;

  public List<CompanyMedicineDto> getAllCompanyMedicine(){

    List<CompanyMedicineDto> pharmacyMedicine = em.createQuery(" select "
            + " new com.searchmedicine.demo.dto.CompanyMedicineDto ("
            + "cm.id, m.name,c.name, cm.url )"
            + " from Medicine m, Company c  "
            + " inner join CompanyMedicine cm on c.id = cm.company.id"
            + " where  c.id = cm.company.id "
            + " and m.id = cm.medicine.id", CompanyMedicineDto.class)
            .getResultList();

    return pharmacyMedicine;
  }
  public List<CompanyMedicineDto> getAllCompanyMedicineisAvailable(){

    List<CompanyMedicineDto> pharmacyMedicine = em.createQuery(" select "
            + " new com.searchmedicine.demo.dto.CompanyMedicineDto ("
            + "cm.id, m.name,c.name, cm.url )"
            + " from Medicine m, Company c  "
            + " inner join CompanyMedicine cm on c.id = cm.company.id"
            + " where  c.id = cm.company.id "
            + " and m.id = cm.medicine.id and cm.isExchange=true", CompanyMedicineDto.class)
            .getResultList();

    return pharmacyMedicine;
  }
  public CompanyMedicine findById(Long id){
    return companyMedicineRepository.getById(id);
  }



}
