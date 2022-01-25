package com.searchmedicine.demo.entities.email;

public interface EmailSender {
    void send(String to, String email);
}