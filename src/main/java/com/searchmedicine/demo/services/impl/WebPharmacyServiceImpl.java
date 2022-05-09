package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.entities.views.*;
import com.searchmedicine.demo.repositories.*;
import com.searchmedicine.demo.services.WebPharmacyService;
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
    private final MedicineRepository medicineRepository;
    private final ImagesRepository imagesRepository;

    @SneakyThrows
    @Override
    public PharmacyHomeInfo getPharmacyHomeInfo(Long pharmacyId) {

        val pharmacy = pharmacyRepository.getById(pharmacyId);
        val reserves = listReserverRepository.findAllByPharmacy(pharmacy.getId());
        val lastMonthReserves = reserves.stream()
                .filter(reserve ->
                        reserve.getReservedTime().isAfter(LocalDateTime.now().minusMonths(1)))
                .collect(Collectors.toList());

        val pharmacyMedicines = pharmacyMedicineRepository.findAllByPharmacyId(pharmacy.getId());
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
    public Pharmacy getPharmacyByUserId(Long userId) {
        return pharmacyRepository.getByUser_Id(userId).orElse(null);
    }

    @SneakyThrows
    @Override
    public List<ListReserver> getPharmacyReserves(Long pharmacyId) {
        val list = listReserverRepository
                .findAllByPharmacy(pharmacyId);
        return list.size() > 0 ? list : null;
    }

    @Override
    public List<PharmacyMedicine> getPharmacyMedicines(Long pharmacyId) {
        val list = pharmacyMedicineRepository.findAllByPharmacyId(pharmacyId);
        return list.size() == 0 ? null : list;
    }

    @Override
    public Response deletePharmacyMedicine(Long pharmacyId, Long pharmacyMedicineId) {
        try {
            listReserverRepository.findAllByPharmacy(pharmacyId).forEach(reserve->{
                if(!reserve.getIsDeleted()){
                    reserve.setIsDeleted(true);
                    listReserverRepository.save(reserve);
                    log.info("Reserve deleted");
                }
            });
            val medicine=pharmacyMedicineRepository.getById(pharmacyMedicineId);
            medicine.setArc(true);
            pharmacyMedicineRepository.save(medicine);
        }
        catch (Exception e){
            log.error(MessageTypes.DELETE_ERROR+"лекарства",e);
            return new Response(1,MessageTypes.DELETE_ERROR+"лекарства");
        }
        return Response.builder()
                .responseMessage("Лекарство успешно удалена")
                .responseCode(0)
                .build();
    }

    @Override
    public Response savePharmacyMedicine(PharmacyMedicine pharmacyMedicine) {
        String resMessage=pharmacyMedicine.getId()==null?
                MessageTypes.ADD_MEDICINE_SUCCESS_MSG : MessageTypes.EDIT_SUCCESS_MSG;
        try {
            if (pharmacyMedicine.getMedicine() == null) {
                return new Response(1," Ошибка : Пустое лекарство");
            }
            val medicine = medicineRepository.getById(pharmacyMedicine.getMedicine().getId());
            pharmacyMedicine.setMedicine(medicine);

            if (pharmacyMedicine.getPharmacy() == null) {
                return new Response(1," Ошибка : Пустая аптека");
            }
            val pharmacy = pharmacyRepository.getById(pharmacyMedicine.getPharmacy().getId());
            pharmacyMedicine.setPharmacy(pharmacy);
            pharmacyMedicineRepository.save(pharmacyMedicine);
        }
        catch (Exception e){
            log.error(MessageTypes.SAVE_ERROR+"лекарства",e);
            return new Response(1,MessageTypes.SAVE_ERROR+"лекарства: "+e.getMessage());
        }
        return new Response(0,resMessage);
    }

    @Override
    public PharmacyMedicineView getDetailedPharmacyMedicine(Long pharmacyMedicineId) {
        val pharmacyMedicine= pharmacyMedicineRepository.getById(pharmacyMedicineId);
        val images= imagesRepository.findAllByMedicineId(pharmacyMedicine.getMedicine().getId());
        return PharmacyMedicineView.builder()
                .pharmacyMedicine(pharmacyMedicine)
                .images(images)
                .build();
    }
}


