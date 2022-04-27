package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.dto.Address;
import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.Response;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.views.AdminHomeInfo;
import com.searchmedicine.demo.entities.views.ChartLine;
import com.searchmedicine.demo.entities.views.PharmacyHomeInfo;
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
    private final FarmGroupRepository farmGroupRepository;
    private final MedicineRepository medicineRepository;
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final PharmacyRepository pharmacyRepository;
    private final CompanyRepository companyRepository;
    private final ListReserverRepository listReserverRepository;
    private final ListWaiterRepository listWaiterRepository;
    private final AddressRepository addressRepository;
    private final PharmacyMedicineRepository pharmacyMedicineRepository;
    private final static String EDIT_SUCCESS_MSG="Изменения сохранены";
    @SneakyThrows
    @Override
    public PharmacyHomeInfo getPharmacyHomeInfo(Users user) {
        Users currentPharmacyUser= usersRepository.findByEmail(user.getEmail());
        Pharmacy pharmacy= pharmacyRepository.getByUser_Id(currentPharmacyUser.getId()).orElse(null);

        val reserves= listReserverRepository.findAllByPharmacyMedicine_Pharmacy_Id(pharmacy.getId());
        val lastMonthReserves= reserves.stream()
                        .filter(reserve->
                                reserve.getReservedTime().isAfter(LocalDateTime.now().minusMonths(1)))
                        .collect(Collectors.toList());

        val pharmacyMedicines = pharmacyMedicineRepository.findAllByPharmacyIdOrderByAddedDateDesc(pharmacy.getId());
        val lastMonthPharmacyMedicines=pharmacyMedicines.stream()
                .filter(medicine->
                        medicine.getAddedDate().isAfter(LocalDateTime.now().minusMonths(1)))
                .collect(Collectors.toList());

        List<ChartLine> chartLineList=new ArrayList<>();
        for(int i=0; i<pharmacyMedicines.size(); i++){
            if(i==10){ break; }
            chartLineList.add(ChartLine.builder()
                    .medicineName(pharmacyMedicines.get(i).getMedicine().getName()+
                            "("+pharmacyMedicines.get(i).getMedicine().getCompany().getName()+")")
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
        return pharmacyRepository.getByUser_Id(id).orElse(null) ;
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
            address1.setName(address.getStreet());
            address1.setNumber(address.getNumber());
            address1.setRegion(pharmacy.getAddress().getRegion());
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
}
