package com.searchmedicine.demo.services;

import com.searchmedicine.demo.dto.ListReserverRequestDto;
import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.entities.views.Response;
import com.searchmedicine.demo.entities.Users;

import java.util.List;

public interface ListReserverService {
  List<ListReserver> getAllIsNotTokenAndIsNotExpired();
  void save(ListReserver listReserver);
  Response saveListReserver(ListReserverRequestDto list, Users users);
  List<ListReserver> getReservationByUser(Long id);
  void delete(ListReserver listReserver);
}
