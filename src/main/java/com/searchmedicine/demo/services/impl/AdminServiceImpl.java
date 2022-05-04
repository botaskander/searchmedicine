package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.dto.ResponseDTO;
import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.entities.views.AdminHomeInfo;
import com.searchmedicine.demo.entities.views.ChartLine;
import com.searchmedicine.demo.repositories.*;
import com.searchmedicine.demo.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
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

    private final static String EDIT_SUCCESS_MSG="Изменения сохранены";

    @SneakyThrows
    @Override
    public List<FarmGroup> getAllFarmGroups() {
        return farmGroupRepository.findAll();
    }

    @Override
    public Response saveFarmGroup(FarmGroup farmGroup) {
        String resMessage=farmGroup.getId()==null? "Группа успешно добавлена!" : EDIT_SUCCESS_MSG;
        try {
            farmGroupRepository.save(farmGroup);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении группы лекарства",e);
            return new Response(1,"Ошибка при сохранении группы лекарства: "+e.getMessage());
        }
        return Response.builder()
                .responseMessage(resMessage)
                .responseCode(0)
                .build();
    }

    @Override
    public Response deleteFarmGroup(Long id) {
        try {
            farmGroupRepository.deleteById(id);
        }
        catch (Exception e){
            log.error("Ошибка при удалении группы лекарства",e);
            return new Response(1,"Ошибка при удалении группы лекарства: "+e.getMessage());
        }
        return Response.builder()
                .responseMessage("Группа успешно удалена")
                .responseCode(0)
                .build();
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

    @Override
    public ResponseDTO saveMedicine(Medicine medicine) {
        Medicine medicine1;
        String resMessage=medicine.getId()==null? "Лекарство успешно добавлено!": EDIT_SUCCESS_MSG;
        try {
            if (medicine.getFarmGroup() == null) {
                return new ResponseDTO(new Response(1," Ошибка в группе: Пустая группа"),null);
            }
            val farmGroup = farmGroupRepository.getById(medicine.getFarmGroup().getId());
            medicine.setFarmGroup(farmGroup);
            if (medicine.getId() == null) {
                medicine.setAddedDate(LocalDateTime.now());
            }
            medicine.setAddedDate(LocalDateTime.now());
            medicine1=medicineRepository.save(medicine);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении лекарства",e);
            return new ResponseDTO(new Response(1,"Ошибка при сохранении лекарства: "+e.getMessage()),null);

        }
        ResponseDTO responseDTO= new ResponseDTO();
        responseDTO.setResponse(new Response(0,resMessage));
        responseDTO.setMedicine(medicine1);
        return responseDTO;
    }

    @Override
    public Response deleteMedicine(Long id) {
        try {
            medicineRepository.deleteById(id);
        }
        catch (Exception e){
            log.error("Ошибка при удалении лекарства",e);
            return new Response(1,"Ошибка при удалении лекарства: "+e.getMessage());
        }
        return Response.builder()
                .responseMessage("Лекарство успешно удалено")
                .responseCode(0)
                .build();
    }

    @Override
    public Country getCountry(Long id) {
        return countryRepository.getById(id);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Response saveCountry(Country country) {
        String resMessage= country.getId()==null? "Страна успешно добавлена!": EDIT_SUCCESS_MSG;
        try {
            countryRepository.save(country);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении страны",e);
            return new Response(1,"Ошибка при сохранении страны: "+e.getMessage());
        }
        return new Response(0,resMessage);
    }

    @Override
    public Response deleteCountry(Long id) {
        try {
            countryRepository.deleteById(id);
        }
        catch (Exception e){
            log.error("Ошибка при удалении страны",e);
            return new Response(1,"Ошибка при удалении страны: "+e.getMessage());
        }
        return Response.builder()
                .responseMessage("Страна успешно удалена")
                .responseCode(0)
                .build();
    }

    @Override
    public City getCity(Long id) {
        return cityRepository.getById(id);
    }

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public Response saveCity(City city) {
        String resMessage=city.getId()==null? "Город успешно добавлен!": EDIT_SUCCESS_MSG;
        try {
            cityRepository.save(city);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении города",e);
            return new Response(1,"Ошибка при сохранении города: "+e.getMessage());
        }
        return new Response(0,resMessage);
    }

    @Override
    public Response deleteCity(Long id) {
        try {
            cityRepository.deleteById(id);
        }
        catch (Exception e){
            log.error("Ошибка при удалении города",e);
            return new Response(1,"Ошибка при удалении города: "+e.getMessage());
        }
        return Response.builder()
                .responseMessage("Город успешно удален")
                .responseCode(0)
                .build();    }

    @Override
    public Region getRegion(Long id) {
        return regionRepository.getById(id);
    }

    @Override
    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    @Override
    public Response saveRegion(Region region) {
        String resMessage=region.getId()==null? "Регион успешно добавлен!" : EDIT_SUCCESS_MSG;
        try {
            if (region.getCity() == null) {
                return new Response(1," Ошибка : Пустой город");
            }
            val city = cityRepository.getById(region.getCity().getId());
            region.setCity(city);
            regionRepository.save(region);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении региона",e);
            return new Response(1,"Ошибка при сохранении региона: "+e.getMessage());
        }
        return new Response(0,resMessage);
    }

    @Override
    public Response deleteRegion(Long id) {
        try {
            regionRepository.deleteById(id);
        }
        catch (Exception e){
            log.error("Ошибка при удалении региона",e);
            return new Response(1,"Ошибка при удалении региона: "+e.getMessage());
        }
        return Response.builder()
                .responseMessage("Регион удален")
                .responseCode(0)
                .build();
    }

    @SneakyThrows
    @Override
    public Company getCompany(Long id) {
        return companyRepository.getById(id);
    }

    @SneakyThrows
    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Response saveCompany(Company company) {
        String resMessage=company.getId()==null? "Компания успешно добавлена!" : EDIT_SUCCESS_MSG;
        try {
            if (company.getCountry() == null) {
                return new Response(1," Ошибка : Пустая страна");
            }
            val country = countryRepository.getById(company.getCountry().getId());
            company.setCountry(country);
            companyRepository.save(company);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении компании",e);
            return new Response(1,"Ошибка при сохранении компании: "+e.getMessage());
        }
        return new Response(0,resMessage);    }

    @Override
    public Response deleteCompany(Long id) {
        try {
            companyRepository.deleteById(id);
        }
        catch (Exception e){
            log.error("Ошибка при удалении компании",e);
            return new Response(1,"Ошибка при удалении компании: "+e.getMessage());
        }
        return Response.builder()
                .responseMessage("Компания удалена")
                .responseCode(0)
                .build();    }

    @Override
    public ListReserver getListReserver(Long id) {
        return listReserverRepository.getById(id);
    }

    @Override
    public List<ListReserver> getAllListReservers() {
        return listReserverRepository.findAll();
    }

    @Override
    public Response saveListReserver(ListReserver listReserver) {
        return null;
    }

    @Override
    public Response deleteListReserver(Long id) {
        return null;
    }

    @Override
    public ListWaiter getListWaiter(Long id) {
        return listWaiterRepository.getById(id);
    }

    @Override
    public List<ListWaiter> getAllListWaiters() {
        return listWaiterRepository.findAll();
    }

    @Override
    public Response saveListWaiter(ListWaiter listWaiter) {
        return null;
    }

    @Override
    public Response deleteListWaiter(Long id) {
        return null;
    }

    @SneakyThrows
    @Override
    public Users getUser(Long id) {
        return usersRepository.getById(id);
    }

    @SneakyThrows
    @Override
    public List<Users> getAllUsers(String roleCode) {

        if(StringUtils.hasText(roleCode)){
            val role=rolesRepository.findByRole(roleCode).get();
                return usersRepository.findAll().stream().filter(user->
                    user.getRoles().contains(role)==true
                ).collect(Collectors.toList());
        }

        return  usersRepository.findAll();
    }

    @SneakyThrows
    @Override
    public AdminHomeInfo getAdminHomeUserInfo() {

        val users=usersRepository.findAllByOrderByRegisterDateDesc();
        val lastMonthUsers= users.stream()
                .filter(user->user.getRegisterDate().isAfter(LocalDateTime.now().minusMonths(1)))
                .collect(Collectors.toList());

        val pharmacies= pharmacyRepository.findAllByOrderByLastUpdateDateDesc();

        val medicines= medicineRepository.findAllByOrderByViewAmountAscSearchAmountAsc();
        List<ChartLine> chartLineList=new ArrayList<>();
        for(int i=0; i<medicines.size(); i++){
            if(i==10){ break; }
            chartLineList.add(ChartLine.builder()
                    .medicineName(medicines.get(i).getName()+"("+medicines.get(i).getCompany().getName()+")")
                    .searchAmount(medicines.get(i).getSearchAmount())
                    .viewAmount(medicines.get(i).getViewAmount())
                    .build());
        }
        val lastMonthMedicines=medicines.stream()
                .filter(medicine->medicine.getAddedDate().isAfter(LocalDateTime.now().minusMonths(1)))
                .collect(Collectors.toList());
        return AdminHomeInfo.builder()
                .chartLineList(chartLineList)
                .lastMonthUsers(lastMonthUsers.size())
                .totalUsers(users.size())
                .lastMonthExchanges(0)
                .totalExchanges(0)
                .lastMonthMedicines(lastMonthMedicines.size())
                .totalMedicines(medicines.size())
                .lastRegisteredUsers(users.size()>3 ? users.subList(0,3) : users)
                .lastUpdatedPharmacies(pharmacies.size()>4? pharmacies.subList(0,4) : pharmacies)
                .build();
    }

    @Override
    public Response saveUser(Users user) {
        return null;
    }

    @Override
    public Response deleteUsers(Long id) {
        return null;
    }

    @Override
    public Roles getRole(Long id) {
        return rolesRepository.getById(id);
    }

    @Override
    public List<Roles> getAllRoles(String roleCode) {

        return rolesRepository.findAll();
    }

    @Override
    public Response saveRole(Roles role) {
        return null;
    }

    @Override
    public Response deleteRoles(Long id) {
        return null;
    }
}

