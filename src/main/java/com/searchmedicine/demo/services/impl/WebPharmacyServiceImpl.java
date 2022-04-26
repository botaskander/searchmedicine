
package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.views.AdminHomeInfo;
import com.searchmedicine.demo.entities.views.ChartLine;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;
import com.searchmedicine.demo.repositories.*;
import com.searchmedicine.demo.services.WebPharmacyService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebPharmacyServiceImpl implements WebPharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final ListReserverRepository listReserverRepository;
    private final PharmacyMedicineRepository pharmacyMedicineRepository;

    @SneakyThrows
    @Override
    public PharmacyHomeInfo getPharmacyHomeInfo(Long id) {

        val pharmacy= pharmacyRepository.getById(id);
        val reserves = listReserverRepository.findAllByPharmacyId(pharmacy.getId());
        val lastMonthReserves = reserves.stream()
                .filter(reserve ->
                        reserve.getReservedTime().isAfter(LocalDateTime.now().minusMonths(1)))
                .collect(Collectors.toList());

        val pharmacyMedicines = pharmacyMedicineRepository.findAllByPharmacyIdOrderByAddedDateDesc(pharmacy.getId());
        val lastMonthPharmacyMedicines = pharmacyMedicines.stream()
                .filter(medicine ->
                        medicine.getAddedDate().isAfter(LocalDateTime.now().minusMonths(1)))
                .collect(Collectors.toList());

        List<ChartLine> chartLineList = new ArrayList<>();
        for (int i = 0; i < pharmacyMedicines.size(); i++) {
            if (i == 10) {
                break;
            }
            chartLineList.add(ChartLine.builder()
                    .medicineName(pharmacyMedicines.get(i).getMedicine().getName() +
                            "(" + pharmacyMedicines.get(i).getMedicine().getCompany().getName() + ")")
                    .searchAmount(pharmacyMedicines.get(i).getMedicine().getSearchAmount())
                    .viewAmount(pharmacyMedicines.get(i).getMedicine().getViewAmount())
                    .build());
        }
        return PharmacyHomeInfo.builder()
                .chartLineList(chartLineList)
                .totalReserves(reserves.size())
                .lastMonthReserves(lastMonthReserves.size())
                .totalMedicines(pharmacyMedicines.size())
                .lastMonthMedicines(lastMonthPharmacyMedicines.size())
                .build();
    }

    @Override
    public Pharmacy getPharmacyByUserId(Long id) {
        return pharmacyRepository.getByUser_Id(id).orElse(null);
    }

    @SneakyThrows
    @Override
    public List<ListReserver> getPharmacyReserves(Long pharmacyId) {
        val list = listReserverRepository
                .findAllByPharmacyIdOrderByReservedTimeDesc(pharmacyId)
                .orElse(null);
        return list;
    }

    @Override
    public List<PharmacyMedicine> getPharmacyMedicines(Long pharmacyId) {
        val list = pharmacyMedicineRepository.findAllByPharmacyIdOrderByAddedDateDesc(pharmacyId);
        return list.size() == 0 ? null : list;
    }
}


