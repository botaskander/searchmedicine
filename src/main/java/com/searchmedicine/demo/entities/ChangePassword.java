package com.searchmedicine.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangePassword {
    private String oldpassword;
    private String newpassword;
}