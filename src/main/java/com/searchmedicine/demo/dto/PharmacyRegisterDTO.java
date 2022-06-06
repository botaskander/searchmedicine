package com.searchmedicine.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyRegisterDTO {
    private String email;
    private String employeeFullName;
    private String pharmacyName;
    private String workStartTime;
    private String workEndTime;
    private String phoneNumber;
    private String whatsappNumber;
    private String addressName;
    private Double addressLongitude;
    private Double addressLatitude;
}
