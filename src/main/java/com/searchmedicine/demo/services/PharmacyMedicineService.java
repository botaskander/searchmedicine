
package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.ListWaiter;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.email.EmailSender;
import com.searchmedicine.demo.repositories.PharmacyMedicineRepository;
import java.util.List;

public interface PharmacyMedicineService {
  List<PharmacyMedicine> getAllPharmacyMedicine(Long id);
  void sendNotification( PharmacyMedicine pharmacyMedicine);
}

    