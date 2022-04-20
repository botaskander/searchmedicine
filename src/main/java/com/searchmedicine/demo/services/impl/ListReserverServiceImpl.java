package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.dto.ListReserverRequestDto;
import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.Response;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.repositories.ListReserverRepository;
import com.searchmedicine.demo.repositories.PharmacyMedicineRepository;
import com.searchmedicine.demo.services.ListReserverService;
import java.sql.Date;
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


 public Response saveListReserver(ListReserverRequestDto reserverRequestDto) {
  ListReserver listReserver = new ListReserver();
  PharmacyMedicine pharmacyMedicine = pharmacyMedicineRepository.findById(reserverRequestDto.getPharmacyMedicineId()).orElse(null);

  LocalDateTime currentDate = LocalDateTime.now();
  LocalDateTime untilTime = null;
  if("12 hours".equals(reserverRequestDto.getUntilTime())){
   untilTime = currentDate.plusHours(12);
  }
  else if ("1 hour".equals(reserverRequestDto.getUntilTime())){
   untilTime = currentDate.plusHours(1);
  }
  else if ("24 hour".equals(reserverRequestDto.getUntilTime())){
   untilTime = currentDate.plusDays(1);
  }
  else if ("48 hour".equals(reserverRequestDto.getUntilTime())){
   untilTime = currentDate.plusDays(2);
  }

  if(pharmacyMedicine != null) {
   listReserver.setMedicine(pharmacyMedicine.getMedicine());
   listReserver.setReservedTime(currentDate);
   listReserver.setPharmacy(pharmacyMedicine.getPharmacy());
   listReserver.setCount(Integer.valueOf(reserverRequestDto.getCount()));
   listReserver.setUntilTime(untilTime);
   listReserver.setUsers(getUser());
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

 private Users getUser(){
  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  if(!(authentication instanceof AnonymousAuthenticationToken)){
   return (Users) authentication.getPrincipal();
  }
  return null;
 }
}
