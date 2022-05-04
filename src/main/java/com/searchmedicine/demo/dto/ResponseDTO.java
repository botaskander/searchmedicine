package com.searchmedicine.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.entities.views.Response;
import com.searchmedicine.demo.entities.views.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ResponseDTO {
    private Response response;
    private Medicine medicine;


}
