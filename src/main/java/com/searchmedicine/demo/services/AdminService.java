package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.FarmGroup;
import com.searchmedicine.demo.entities.Medicine;

import java.util.List;

public interface AdminService {
    List<FarmGroup> getAllFarmGroups();

    boolean saveFarmGroup(FarmGroup farmGroup);

    boolean deleteFartmGroup(Long id);

    FarmGroup getFarmGroup(Long id);

    Medicine getMedicine(Long id);

    List<Medicine> getAllMedicines();

    void saveMedicine(Medicine medicine);

    void deleteMedicine(Long id);


}
