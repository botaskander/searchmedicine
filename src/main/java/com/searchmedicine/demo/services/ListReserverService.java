package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.ListReserver;
import java.util.List;

public interface ListReserverService {
  List<ListReserver> getAllIsNotTokenAndIsNotExpired();
  void save(ListReserver listReserver);
}
