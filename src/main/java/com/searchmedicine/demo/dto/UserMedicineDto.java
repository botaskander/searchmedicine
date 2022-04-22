package com.searchmedicine.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMedicineDto {

  private String fullName;
  private String phoneNumber;
  private String medicineName;
  private String instructions;
  private String indications;
  private String description;
  private String companyName;
  private String url;
  private String address;


}
