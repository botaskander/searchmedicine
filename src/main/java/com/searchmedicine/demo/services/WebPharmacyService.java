package com.searchmedicine.demo.services;


import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;

public interface WebPharmacyService {
    PharmacyHomeInfo getPharmacyHomeInfo(Users user);
}
