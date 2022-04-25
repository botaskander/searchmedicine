package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.entities.ListWaiter;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.Response;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.repositories.ListWaiterRepository;
import com.searchmedicine.demo.repositories.MedicineRepository;
import com.searchmedicine.demo.repositories.UsersRepository;
import com.searchmedicine.demo.services.ListWaiterService;
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
public class ListWaiterServiceImpl implements ListWaiterService {
  private final ListWaiterRepository listWaiterRepository;
  private final MedicineRepository medicineRepository;


  public List<ListWaiter> getNotification(Long medicineId){
    return listWaiterRepository.getNotificationItems(medicineId);
  }

  public Response saveListWaiter(Long id, Users users) {
    ListWaiter listWaiter = new ListWaiter();
    Medicine medicine = medicineRepository.findById(id).orElse(null);

    if(medicine != null) {
      listWaiter.setMedicine(medicine);
      listWaiter.setUsers(users);
    }
    else {
      return  new Response(1,"Ошибка при сохранении ListWaiter, лекарства пустая : NullPointer Exception");
    }

    try {
      listWaiterRepository.save(listWaiter);
    }
    catch (Exception e){
      log.error("Ошибка при сохранении ListWaiter",e);
      return new Response(1,"Ошибка при сохранении ListWaiter : "+e.getMessage());
    }
    return Response.builder()
        .responseMessage("Успешно ListWaiter добавлено")
        .responseCode(0)
        .build();
  }

  @Override
  public List<ListWaiter> getWaiterByUserId(Long id) {
    return listWaiterRepository.findAllByUsers_Id(id);
  }

  @Override
  public void deleteListWaiter(ListWaiter listWaiter) {
    listWaiterRepository.delete(listWaiter);
  }

  @Override
  public ListWaiter save(ListWaiter listWaiter) {
    return listWaiterRepository.save(listWaiter);
  }

  private Users getUser(){
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(!(authentication instanceof AnonymousAuthenticationToken)){
      return (Users) authentication.getPrincipal();
    }
    return null;
  }

}
