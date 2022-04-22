package com.searchmedicine.demo.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyMedicineDto {

  private String pharmacyName;
  private String address;
  private LocalTime workStartTime;
  private LocalTime workEndTime;
  private String whatsappNumber;
  private String offPhone;
  private String phoneNumber;
  private String medicineName;
  private int count;
  private double price;
  private String instructions;
  private String indications;
  private String description;
  private String companyName;
  private String url;

}
