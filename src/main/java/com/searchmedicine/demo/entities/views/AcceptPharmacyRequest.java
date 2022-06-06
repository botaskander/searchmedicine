package com.searchmedicine.demo.entities.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcceptPharmacyRequest {
    private String message;
    private boolean isAccepted;
    private Long requestId;
}
