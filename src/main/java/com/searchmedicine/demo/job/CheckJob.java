package com.searchmedicine.demo.job;

import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.services.impl.ListReserverServiceImpl;


import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CheckJob {
  private final ListReserverServiceImpl listReserverServiceImpl;

  @Scheduled(fixedDelay = 60000)
  public void run(){
//    System.out.println("*********************************");
//    System.out.println("Start check");
//    checkBook();
  }

  public void checkBook(){
    List<ListReserver> listReservers = listReserverServiceImpl.getAllIsNotTokenAndIsNotExpired();
    LocalDateTime todayDate = LocalDateTime.now();
    for(ListReserver lr: listReservers){
      {
        if(todayDate.isAfter(lr.getUntilTime())){
          lr.setIsExpired(true);
          listReserverServiceImpl.save(lr);
        }
      }
    }
    System.out.println("Checked list reserver");
  }
}
