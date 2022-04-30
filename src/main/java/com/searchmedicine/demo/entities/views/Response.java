package com.searchmedicine.demo.entities.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response {
    private int responseCode;  // 1 - error, 0 - success
    private String responseMessage;
}
