package com.searchmedicine.demo.entities.views;


import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminHomeInfo {
//    private List<SearchResult> searchResults;
//    private List<ViewResult> viewResults;
    private List<ChartLine> chartLineList;
    private int totalUsers;
    private int lastMonthUsers;
    private int totalMedicines;
    private int lastMonthMedicines;
    private int totalExchanges;
    private int lastMonthExchanges;
    private List<Pharmacy> lastUpdatedPharmacies;
    private List<Users> lastRegisteredUsers;
}
