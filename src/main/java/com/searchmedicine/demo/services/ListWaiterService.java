package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.ListWaiter;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.entities.Response;
import com.searchmedicine.demo.entities.Users;
import java.util.List;

public interface ListWaiterService {
  List<ListWaiter> getNotification(Long medicineId);
  Response saveListWaiter(Long id, Users users);
  List<ListWaiter> getWaiterByUserId(Long id);
  void deleteListWaiter(ListWaiter listWaiter);
  ListWaiter  save(ListWaiter listWaiter);
}
