package com.searchmedicine.demo.services;


import com.searchmedicine.demo.dto.Address;
import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.Response;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;

public interface WebPharmacyService {
    PharmacyHomeInfo getPharmacyHomeInfo(Users user);

    Pharmacy getPharmacyByUserId(Long id);

    Response saveProfile(Pharmacy pharmacy);

    Response saveAddress(Address address);

    Pharmacy getPharmacy(Long id);
}
