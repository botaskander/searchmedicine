package com.searchmedicine.demo.entities.views;

import lombok.Data;

@Data
public class ErrorTypes {
    private final String NOT_CORRECT_EMAIL="Неправильный логин";
    private final String NOT_CORRECT_PASS="Неправильный пароль";
    private final String NOT_PHARMACY="Упс, кажется вы не аптека";
}
