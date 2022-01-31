package com.searchmedicine.demo.entities.dto;

import com.searchmedicine.demo.entities.Company;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.entities.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyMedicineDto {

  private Medicine medicine;
  private Pharmacy pharmacy;
  private Company company;
  private double price;
  private String imageUrl;

}
