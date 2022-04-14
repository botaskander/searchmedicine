package com.searchmedicine.demo.entities.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChartLine {
    private String medicineName;
    private int viewAmount;
    private int searchAmount;

}

