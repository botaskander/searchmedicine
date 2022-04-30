package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.dto.ListReserverRequestDto;
import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.views.Response;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.repositories.ListReserverRepository;
import com.searchmedicine.demo.repositories.PharmacyMedicineRepository;
import com.searchmedicine.demo.services.ListReserverService;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListReserverServiceImpl implements ListReserverService {
private final ListReserverRepository listReserverRepository;
private final PharmacyMedicineRepository pharmacyMedicineRepository;

 public List<ListReserver> getAllIsNotTokenAndIsNotExpired(){
    return  listReserverRepository.getAllIsNotTokenAndIsNotExpired();
  }

  public void save(ListReserver listReserver){
   listReserverRepository.save(listReserver);
  }


 public Response saveListReserver(ListReserverRequestDto reserverRequestDto,Users users) {
  ListReserver listReserver = new ListReserver();
  PharmacyMedicine pharmacyMedicine = pharmacyMedicineRepository.findById(reserverRequestDto.getPharmacyMedicineId()).orElse(null);

  LocalDateTime currentDate = LocalDateTime.now();
  LocalDateTime untilTime = null;
  if("на 12 часов".equals(reserverRequestDto.getUntilTime())){
   untilTime = currentDate.plusHours(12);
  }
  else if ("на час".equals(reserverRequestDto.getUntilTime())){
   untilTime = currentDate.plusHours(1);
  }
  else if ("на 24 часов".equals(reserverRequestDto.getUntilTime())){
   untilTime = currentDate.plusDays(1);
  }
  else if ("на 48 часов".equals(reserverRequestDto.getUntilTime())){
   untilTime = currentDate.plusDays(2);
  }
  System.out.println(reserverRequestDto.getUntilTime());

  if(pharmacyMedicine != null) {
   listReserver.setPharmacyMedicine(pharmacyMedicine);
   listReserver.setIsExpired(false);
   listReserver.setIsDeleted(false);
   listReserver.setIsTook(false);
   listReserver.setReservedTime(currentDate);
   listReserver.setCount(Integer.valueOf(reserverRequestDto.getCount()));
   listReserver.setUntilTime(untilTime);
   listReserver.setUsers(users);
  }
  else {
   return  new Response(1,"Ошибка при сохранении ListWaiter, лекарства пустая : NullPointer Exception");
  }
  try {
   listReserverRepository.save(listReserver);
  }
  catch (Exception e){
   log.error("Ошибка при сохранении ListWaiter",e);
   return new Response(1,"Ошибка при сохранении ListWaiter : "+e.getMessage());
  }
  return Response.builder()
      .responseMessage("Успешно ListReserver добавлено")
      .responseCode(0)
      .build();
 }

 @Override
 public List<ListReserver> getReservationByUser(Long id) {
  return listReserverRepository.findAllByUsersId(id);
 }

 @Override
 public void delete(ListReserver listReserver) {
  listReserverRepository.delete(listReserver);
 }

 private Users getUser(){
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  if(!(authentication instanceof AnonymousAuthenticationToken)){
   return (Users) authentication.getPrincipal();
  }
  return null;
 }
}
