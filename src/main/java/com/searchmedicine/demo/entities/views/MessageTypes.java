package com.searchmedicine.demo.entities.views;

import lombok.Data;

@Data
public class MessageTypes {
    public static final String NOT_CORRECT_EMAIL="Неправильный логин";
    public static final String NOT_CORRECT_PASS="Неправильный пароль";
    public static final String NOT_PHARMACY="Упс, кажется вы не аптека";
    public static final  String EDIT_SUCCESS_MSG="Изменения сохранены";

    public static final String ADD_MEDICINE_SUCCESS_MSG="Лекарство успешно добавлено!";
    public static final String ADD_FARM_GROUP_SUCCESS_MSG="Фарм группа успешно добавлена!";
    public static final String ADD_COUNTRY_SUCCESS_MSG="Страна успешно добавлена!";
    public static final String ADD_COMPANY_SUCCESS_MSG="Комнапия успешно дабавлена!";
    public static final String ADD_RESERVE_SUCCESS_MSG="Резерв успешно добавлен!";

    public static final String DELETE_ERROR="Произошла ошибка при удалении ";
    public static final String SAVE_ERROR="Произошла ошибка при сохранении ";

    public static final String DELETE_MEDICINE_SUCCESS_MSG="Лекарство успешно удалено!";
    public static final String DELETE_FARM_GROUP_SUCCESS_MSG="Фарм группа успешно удалена!";
    public static final String DELETE_COUNTRY_SUCCESS_MSG="Страна успешно удалена!";
    public static final String DELETE_COMPANY_SUCCESS_MSG="Комнапия успешно удалена!";
    public static final String DELETE_RESERVE_SUCCESS_MSG="Резерв успешно удален!";
}
