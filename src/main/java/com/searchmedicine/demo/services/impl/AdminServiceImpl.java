package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.repositories.*;
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
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final PharmacyRepository pharmacyRepository;
    private final ListReserverRepository listReserverRepository;
    private final ListWaiterRepository listWaiterRepository;

    @SneakyThrows
    @Override
    public List<FarmGroup> getAllFarmGroups() {
        return farmGroupRepository.findAll();
    }

    @Override
    public Response saveFarmGroup(FarmGroup farmGroup) {
        try {
            farmGroupRepository.save(farmGroup);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении группы лекарства",e);
            return new Response(1,"Ошибка при сохранении группы лекарства: "+e.getMessage());
        }
        return Response.builder()
                .responseMessage(farmGroup.getId()==null? "Группа успешно добавлена!":" Изменения сохранены")
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
    public Response saveMedicine(Medicine medicine) {
        try {
            if (medicine.getFarmGroup() == null) {
                return new Response(1," Ошибка в группе: Пустая группа");
            }
            val farmGroup = farmGroupRepository.getById(medicine.getFarmGroup().getId());
            medicine.setFarmGroup(farmGroup);
            medicineRepository.save(medicine);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении лекарства",e);
            return new Response(1,"Ошибка при сохранении лекарства: "+e.getMessage());
        }
        return Response.builder()
                .responseMessage(medicine.getId()==null? "Лекарство успешно добавлено!":" Изменения сохранены")
                .responseCode(0)
                .build();
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
        System.out.println("CCCCC "+country.getId());
        try {
            countryRepository.save(country);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении страны",e);
            return new Response(1,"Ошибка при сохранении страны: "+e.getMessage());
        }
        return Response.builder()
                .responseMessage(country.getId()==null? "Страна успешно добавлена!":" Изменения сохранены")
                .responseCode(0)
                .build();
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
        try {
            cityRepository.save(city);
        }
        catch (Exception e){
            log.error("Ошибка при сохранении города",e);
            return new Response(1,"Ошибка при сохранении города: "+e.getMessage());
        }
        return Response.builder()
                .responseMessage(city.getId()==null? "Город успешно добавлен!":" Изменения сохранены")
                .responseCode(0)
                .build();
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
        return Response.builder()
                .responseMessage(region.getId()==null? "Регион успешно добавлен!":" Изменения сохранены")
                .responseCode(0)
                .build();    }

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

    @Override
    public Users getUser(Long id) {
        return usersRepository.getById(id);
    }

    @Override
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
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
    public List<Roles> getAllRoles() {
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

