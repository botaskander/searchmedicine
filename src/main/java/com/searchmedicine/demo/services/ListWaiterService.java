package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.ListWaiter;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.entities.Response;
import java.util.List;

public interface ListWaiterService {
  List<ListWaiter> getNotification(Long medicineId);
  Response saveListWaiter(Long id);
}
