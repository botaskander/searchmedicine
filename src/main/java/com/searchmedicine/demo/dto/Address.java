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
    private String lonlat;
}
