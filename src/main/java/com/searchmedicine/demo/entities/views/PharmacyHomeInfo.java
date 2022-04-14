package com.searchmedicine.demo.entities.views;

import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyHomeInfo {
    private List<ChartLine> chartLineList;
    private int totalMedicines;
    private int lastMonthMedicines;
    private int totalReserves;
    private int lastMonthReserves;

}
