package com.searchmedicine.demo.dto;

import com.searchmedicine.demo.entities.Medicine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private Long id;
    private String street;
    private String number;
    private String city;
    private Double lang;
    private Double lat;
}
