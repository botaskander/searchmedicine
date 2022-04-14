package com.searchmedicine.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicineExchange {
    private Long id;
    private String description;
    private String month;
    private String year;
    private String idMedicine;
    private String nameMedicine;
    private String imageUrl;
    private String phone;
}
