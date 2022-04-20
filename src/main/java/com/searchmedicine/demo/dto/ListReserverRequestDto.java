package com.searchmedicine.demo.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListReserverRequestDto {
  private Long pharmacyMedicineId;
  private String untilTime;
  private String count;
}
