package com.searchmedicine.demo.dto;

import com.searchmedicine.demo.entities.Address;
import com.searchmedicine.demo.entities.Medicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineDto {
  private Long id;
  private String type;
  private Medicine medicine;
  private double price;
  private String owner;
  private Address address;
  private String phoneNumber;

}
