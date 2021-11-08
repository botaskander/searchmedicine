package com.searchmedicine.demo.payload.response;



import com.searchmedicine.demo.entities.Roles;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String fullname;
    private String email;
    private List<Roles> roles;


    public JwtResponse(String accessToken, Long id, String fullname, String email, List<Roles> roles) {
        this.token = accessToken;
        this.id = id;
        this.fullname = fullname;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public List<Roles> getRoles() {
        return roles;
    }
}
