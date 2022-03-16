package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.repositories.ListReserverRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListReserverService {
private final ListReserverRepository listReserverRepository;

 public List<ListReserver> getAllIsNotTokenAndIsNotExpired(){
    return  listReserverRepository.getAllIsNotTokenAndIsNotExpired();
  }

  public void save(ListReserver listReserver){
   listReserverRepository.save(listReserver);
  }
}
