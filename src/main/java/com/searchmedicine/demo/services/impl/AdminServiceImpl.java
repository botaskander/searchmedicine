package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.entities.FarmGroup;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.repositories.FarmGroupRepository;
import com.searchmedicine.demo.repositories.MedicineRepository;
import com.searchmedicine.demo.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final FarmGroupRepository farmGroupRepository;
    private final MedicineRepository medicineRepository;

    @SneakyThrows
    @Override
    public List<FarmGroup> getAllFarmGroups() {
        val list=farmGroupRepository.findAll();
        return  list;
    }

    @Override
    public boolean saveFarmGroup(FarmGroup farmGroup) {
        boolean isSaved=false;
        try{
            farmGroupRepository.save(farmGroup);
            isSaved=true;
        } catch (Exception e) {
            log.error("Error on saving farm group",e);
        }
        return isSaved;
    }

    @Override
    public boolean deleteFartmGroup(Long id) {
        boolean isDeleted=false;
        if(id==null) return false;
        try{
            farmGroupRepository.deleteById(id);
            isDeleted=true;
        } catch (Exception e) {
            log.error("Error on deleting farm group",e);
        }
        return isDeleted;
    }

    @Override
    public FarmGroup getFarmGroup(Long id) {
        return farmGroupRepository.findById(id).orElse(null);
    }

    @Override
    public Medicine getMedicine(Long id) {
        if(id!=null){
            return medicineRepository.getById(id);
        }
        return null;
    }

    @Override
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    @SneakyThrows
    @Override
    public void saveMedicine(Medicine medicine) {
       medicineRepository.save(medicine);
    }

    @SneakyThrows
    @Override
    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }
}

