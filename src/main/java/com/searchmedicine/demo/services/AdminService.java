package com.searchmedicine.demo.services;

import com.searchmedicine.demo.dto.ResponseDTO;
import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.entities.views.AdminHomeInfo;

import java.util.List;

public interface AdminService {
  List<FarmGroup> getAllFarmGroups();

  Response saveFarmGroup(FarmGroup farmGroup);

  Response deleteFarmGroup(Long id);

  FarmGroup getFarmGroup(Long id);

  Medicine getMedicine(Long id);

  List<Medicine> getAllMedicines();

  ResponseDTO saveMedicine(Medicine medicine);

  Response deleteMedicine(Long id);

  Country getCountry(Long id);

  List<Country> getAllCountries();

  Response saveCountry(Country country);

  Response deleteCountry(Long id);

  City getCity(Long id);

  List<City> getAllCities();

  Response saveCity(City city);

  Response deleteCity(Long id);

  Region getRegion(Long id);

  List<Region> getAllRegions();

  Response saveRegion(Region region);

  Response deleteRegion(Long id);

  Company getCompany(Long id);

  List<Company> getAllCompanies();

  Response saveCompany(Company company);

  Response deleteCompany(Long id);

  ListReserver getListReserver(Long id);

  List<ListReserver> getAllListReservers();

  Response saveListReserver(ListReserver listReserver);

  Response deleteListReserver(Long id);

  ListWaiter getListWaiter(Long id);

  List<ListWaiter> getAllListWaiters();

  Response saveListWaiter(ListWaiter listWaiter);

  Response deleteListWaiter(Long id);

  Users getUser(Long id);

  List<Users> getAllUsers(String roleCode);

  AdminHomeInfo getAdminHomeUserInfo();

  Response saveUser(Users user);

  Response deleteUsers(Long id);

  Roles getRole(Long id);

  List<Roles> getAllRoles(String roleCode);

  Response saveRole(Roles role);

  Response deleteRoles(Long id);
}
