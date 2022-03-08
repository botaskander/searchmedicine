package com.searchmedicine.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PharmacyMedicineDto {

  private Long id;
  private String pharmacy;
  private String medicine;
  private String company;
  private String imageUrl;
  private double price;
}
