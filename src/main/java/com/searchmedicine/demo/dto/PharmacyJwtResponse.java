package com.searchmedicine.demo.dto;

import com.searchmedicine.demo.entities.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class PharmacyJwtResponse implements Serializable {
    private static final long serialVersionUID = 987654321L;
    private final String jwtToken;
    private Pharmacy pharmacy;

    public PharmacyJwtResponse(String jwtToken,Pharmacy pharmacy) {
        this.jwtToken = jwtToken;
        this.pharmacy=pharmacy;
    }
}
