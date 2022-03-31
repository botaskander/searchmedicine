package com.searchmedicine.demo.job;

import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.services.ListReserverService;


import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CheckJob {
  private final ListReserverService listReserverService;

  @Scheduled(fixedDelay = 60000)
  public void run(){
//    System.out.println("*********************************");
//    System.out.println("Start check");
//    checkBook();
  }

  public void checkBook(){
    List<ListReserver> listReservers = listReserverService.getAllIsNotTokenAndIsNotExpired();
    LocalDateTime todayDate = LocalDateTime.now();
    for(ListReserver lr: listReservers){
      {
        if(todayDate.isAfter(lr.getUntilTime())){
          lr.setIsExpired(true);
          listReserverService.save(lr);
        }
      }
    }
    System.out.println("Checked list reserver");
  }
}
