package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.dto.Address;
import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.views.ChartLine;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;
import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.entities.views.*;
import com.searchmedicine.demo.repositories.*;
import com.searchmedicine.demo.services.PharmacyMedicineService;
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

import static com.searchmedicine.demo.entities.views.MessageTypes.EDIT_SUCCESS_MSG;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebPharmacyServiceImpl implements WebPharmacyService {

    private final MedicineRepository medicineRepository;
    private final PharmacyRepository pharmacyRepository;
    private final ListReserverRepository listReserverRepository;
    private final AddressRepository addressRepository;
    private final PharmacyMedicineRepository pharmacyMedicineRepository;
    private final PharmacyMedicineService pharmacyMedicineService;

    @SneakyThrows
    @Override
    public PharmacyHomeInfo getPharmacyHomeInfo(Long pharmacyId) {

        val pharmacy = pharmacyRepository.getById(pharmacyId);
        val reserves = listReserverRepository.findAllByPharmacy(pharmacy.getId());
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
    public Pharmacy getPharmacyByUserId(Long userId) {
        return pharmacyRepository.getByUser_Id(userId).orElse(null);
    }

    @Override
    public Response saveProfile(Pharmacy pharmacy) {
        String resMessage=pharmacy.getId()==null? "Компания успешно добавлена!" : EDIT_SUCCESS_MSG;
        try {
            if (pharmacy == null) {
                return new Response(1," Ошибка : Пустое поле");
            }

            pharmacyRepository.save(pharmacy);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении ",e);
            return new Response(1,"Ошибка при сохранении : "+e.getMessage());
        }
        return new Response(0,resMessage);
    }

    @Override
    public Response saveAddress(Address address) {
        String resMessage=EDIT_SUCCESS_MSG;
        try {
            if (address == null) {
                return new Response(1," Ошибка : Пустое поле");
            }
            Pharmacy pharmacy=pharmacyRepository.findById(address.getId()).get();
            System.out.println(pharmacy);
            com.searchmedicine.demo.entities.Address address1 = new com.searchmedicine.demo.entities.Address();
            address1.setName(address.getName());
            address1.setLatitude(address.getLat());
            address1.setLongitude(address.getLang());
            System.out.println(address1);
            com.searchmedicine.demo.entities.Address address2=addressRepository.save(address1);
            System.out.println(address2);
            pharmacy.setAddress(address2);
            pharmacyRepository.save(pharmacy);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении ",e);
            return new Response(1,"Ошибка при сохранении : "+e.getMessage());
        }
        return new Response(0,resMessage);
    }

    @Override
    public Pharmacy getPharmacy(Long id) {
        return pharmacyRepository.findById(id).get();
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
        val list = pharmacyMedicineRepository.findAllByPharmacyIdOrderByAddedDateDesc(pharmacyId);
        return list.size() == 0 ? null : list;
    }

    @Override
    public Response deletePharmacyMedicine(Long pharmacyId, Long pharmacyMedicineId) {
        try {
            listReserverRepository.findAllByPharmacy(pharmacyId).forEach(reserve->{
                listReserverRepository.delete(reserve);
                log.info("Reserve deleted");
            });
            pharmacyMedicineRepository.deleteById(pharmacyMedicineId);
        }
        catch (Exception e){
            log.error(MessageTypes.DELETE_ERROR+"компании",e);
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
                MessageTypes.ADD_MEDICINE_SUCCESS_MSG : EDIT_SUCCESS_MSG;
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
            pharmacyMedicineService.sendNotification(pharmacyMedicine);

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
        return PharmacyMedicineView.builder()
                .pharmacyMedicine(pharmacyMedicine)
                .build();
    }
}


