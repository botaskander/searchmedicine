package com.searchmedicine.demo.services;


import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;

import java.util.List;

public interface WebPharmacyService {
    PharmacyHomeInfo getPharmacyHomeInfo(Long id);

    Pharmacy getPharmacyByUserId(Long id);

    List<ListReserver> getPharmacyReserves(Long pharmacyId);

    List<PharmacyMedicine> getPharmacyMedicines(Long pharmacyId);
}
