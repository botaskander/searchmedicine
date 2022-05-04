package com.searchmedicine.demo.dto;

import com.searchmedicine.demo.entities.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private String fullName;
    private String email;
    private List<Roles> roles;
}
