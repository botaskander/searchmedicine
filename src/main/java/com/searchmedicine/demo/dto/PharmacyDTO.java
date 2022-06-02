package com.searchmedicine.demo.dto;

import com.searchmedicine.demo.entities.Address;
import com.searchmedicine.demo.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PharmacyDTO {
    private Long pharmacyId;
    private String name;
    private String workStartTime;
    private String workEndTime;
    private String phoneNumber;
    private String whatsappNumber;
    private String offPhone;
    private String oldPassword;
    private String newPassword;
}
