
package com.searchmedicine.demo.services;

import com.searchmedicine.demo.dto.MedicineDto;
import com.searchmedicine.demo.dto.PharmacyMedicineDto;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.Users;
import java.util.List;

public interface PharmacyMedicineService {
  List<MedicineDto> getAllPharmacyUserMedicine(Long id, String type,Boolean isAsc, Users user);
  void sendNotification( PharmacyMedicine pharmacyMedicine);
  PharmacyMedicineDto getPharmacyMedicine(Long id);

  PharmacyMedicine getPharmacyMedicineById(Long id);
}

