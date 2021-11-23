package com.ponkratov.airport.server.controller.util.validator;

public class ParameterRegexp {
    public static final String FIRST_LAST_SUR_NAME_REGEXP = "[a-zA-Zа-яА-Я- ]{1,30}";
    public static final String PASSWORD_REGEXP = "(.){5,40}";
    public static final String USERNAME_REGEXP = "(\\w){1,255}";
    public static final String FIRST_LAST_SUR_NAME_SEARCH_REGEXP = "[a-zA-Zа-яА-ЯЁё -]*";
}
