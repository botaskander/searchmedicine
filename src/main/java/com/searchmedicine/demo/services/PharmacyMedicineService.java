package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.PharmacyMedicine;
import java.util.List;

public interface PharmacyMedicineService {
  List<PharmacyMedicine> getAllPharmacyMedicine(Long id);
  void sendNotification( PharmacyMedicine pharmacyMedicine);
}
