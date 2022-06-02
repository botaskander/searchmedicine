package com.searchmedicine.demo.services;


import com.searchmedicine.demo.dto.Address;
import com.searchmedicine.demo.dto.PharmacyDTO;
import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;
import com.searchmedicine.demo.entities.views.PharmacyMedicineView;
import com.searchmedicine.demo.entities.views.Response;

import java.util.List;

public interface WebPharmacyService {
    PharmacyHomeInfo getPharmacyHomeInfo(Long pharmacyId);

    Pharmacy getPharmacyByUserId(Long userId);

    List<ListReserver> getPharmacyReserves(Long pharmacyId);

    List<PharmacyMedicine> getPharmacyMedicines(Long pharmacyId);

    Response saveProfile(Pharmacy pharmacy);

    Response saveAddress(Address address);

    Pharmacy getPharmacy(Long id);

    Response deletePharmacyMedicine(Long pharmacyId, Long pharmacyMedicineId);

    Response savePharmacyMedicine(PharmacyMedicine pharmacyMedicine);

    PharmacyMedicineView getDetailedPharmacyMedicine(Long pharmacyMedicineId);

    Response editPassword(PharmacyDTO pharmacyDTO,Users users);
}
