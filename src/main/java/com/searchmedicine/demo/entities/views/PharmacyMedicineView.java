package com.searchmedicine.demo.entities.views;

import com.searchmedicine.demo.entities.Image;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyMedicineView {
    private PharmacyMedicine pharmacyMedicine;
    private List<Image> images;
}
