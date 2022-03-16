package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.ListWaiter;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.repositories.ListWaiterRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListWaiterService {
  private final ListWaiterRepository listWaiterRepository;

  public List<ListWaiter> getNotification(Long medicineId){
    return listWaiterRepository.getNotificationItems(medicineId);
  }

}
