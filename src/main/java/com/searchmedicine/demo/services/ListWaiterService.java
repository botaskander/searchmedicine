package com.searchmedicine.demo.services;

import com.searchmedicine.demo.entities.ListWaiter;
import java.util.List;

public interface ListWaiterService {
  List<ListWaiter> getNotification(Long medicineId);
}
