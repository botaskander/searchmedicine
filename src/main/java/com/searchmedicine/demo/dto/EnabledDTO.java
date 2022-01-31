package com.searchmedicine.demo.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming
public class EnabledDTO {
    boolean enabled;
}
