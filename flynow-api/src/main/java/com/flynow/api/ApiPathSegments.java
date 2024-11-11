package com.flynow.api;

public class ApiPathSegments {

    public static final String DOMAIN = "http://localhost:8080";
    public static final String API = "/api";
    public static final String API_VERSION = "/v1";
    public static final String BASE_PATH = API + API_VERSION;
    public static final String ABSOLUTE_BASE_PATH = DOMAIN + API + API_VERSION;


    //Test
    public static final String TEST = "/test";
    //LEVEL 1
    public static final String TEST_CREATE_DATA = "/create-data";
    //LEVEL 2
    public static final String TEST_CREATE_ALL = "/all";
    public static final String TEST_ROLES = "/roles";
    public static final String TEST_USERS = "/users";
    public static final String TEST_COMPANY = "/company";
    public static final String TEST_AIRCRAFT_TYPE = "/aircraft-type";
    //PATHS
    public static final String TEST_CREATE_ROLES_URL = TEST_CREATE_DATA+TEST_ROLES;
    public static final String TEST_CREATE_USERS_URL = TEST_CREATE_DATA+TEST_USERS;
    public static final String TEST_CREATE_COMPANY_URL = TEST_CREATE_DATA+TEST_COMPANY;
    public static final String TEST_CREATE_AIRCRAFT_TYPE_URL = TEST_CREATE_DATA+TEST_AIRCRAFT_TYPE;
    public static final String TEST_CREATE_ALL_URL = TEST_CREATE_DATA+TEST_CREATE_ALL;

    //Authentication
    public static final String AUTHENTICATION = "/authentication";
    //LEVEL 1
    public static final String AUTHENTICATION_ALL = "/all";
    public static final String AUTHENTICATION_LOGIN = "/login";
    public static final String AUTHENTICATION_REGISTER = "/register";
    //PATHS
    public static final String AUTHENTICATION_LOGIN_URL = AUTHENTICATION+AUTHENTICATION_LOGIN;
    public static final String AUTHENTICATION_REGISTER_URL = AUTHENTICATION+AUTHENTICATION_REGISTER;

//    public static final String  = "/";
}
