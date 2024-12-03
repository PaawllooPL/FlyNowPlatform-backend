package com.flynow.api;

public class ApiPathSegments {

    public static final String DOMAIN = "http://localhost:8080";
    public static final String API = "/api";
    public static final String API_VERSION = "/v1";
    public static final String BASE_PATH = API + API_VERSION;
    public static final String ABSOLUTE_BASE_PATH = DOMAIN + API + API_VERSION;

    public static final String ID = "/{id}";

    //TEST
    public static final String TEST = "/test";
    //Level 1
    public static final String TEST_CREATE_DATA = "/create-data";
    public static final String TEST_DELETE_DATA = "/delete-data";
    //Level 2
    public static final String TEST_CREATE_ALL = "/all";
    public static final String TEST_ROLES = "/roles";
    public static final String TEST_USERS = "/users";
    public static final String TEST_COMPANY = "/company";
    public static final String TEST_AIRCRAFT_TYPE = "/aircraft-type";
    public static final String TEST_COMMENTS = "/comments";
    public static final String TEST_FLIGHTS = "/flights";
    //Paths
    public static final String TEST_CREATE_ROLES_URL = TEST_CREATE_DATA+ TEST_ROLES;
    public static final String TEST_CREATE_USERS_URL = TEST_CREATE_DATA+ TEST_USERS;
    public static final String TEST_CREATE_COMPANY_URL = TEST_CREATE_DATA+ TEST_COMPANY;
    public static final String TEST_CREATE_AIRCRAFT_TYPE_URL = TEST_CREATE_DATA+ TEST_AIRCRAFT_TYPE;
    public static final String TEST_CREATE_FLIGHT_URL = TEST_CREATE_DATA+ TEST_FLIGHTS;
    public static final String TEST_CREATE_COMMENTS_URL = TEST_CREATE_DATA+ TEST_COMMENTS;
    public static final String TEST_CREATE_ALL_URL = TEST_CREATE_DATA+ TEST_CREATE_ALL;
    public static final String TEST_DELETE_FLIGHT_URL = TEST_DELETE_DATA + TEST_FLIGHTS + ID;

    //AUTHENTICATION
    public static final String AUTHENTICATION = "/authentication";
    //Level 1
    public static final String AUTHENTICATION_ALL = "/all";
    public static final String AUTHENTICATION_LOGIN = "/login";
    public static final String AUTHENTICATION_REGISTER = "/register";
    public static final String AUTHENTICATION_TEST = "/auth-test";
    //Paths
    public static final String AUTHENTICATION_LOGIN_URL = AUTHENTICATION+AUTHENTICATION_LOGIN;
    public static final String AUTHENTICATION_REGISTER_URL = AUTHENTICATION+AUTHENTICATION_REGISTER;

    //IMAGE
    public static final String IMAGE = "/image";
    //Level 1
    public static final String IMAGE_FILENAME_PARAMETER = "/{filename}";
    //Level 2

    //OFFER
    public static final String OFFERS = "/offers";
    public static final String FLIGHT_ID = "/{flightId}";
    //Level 1
    public static final String OFFERS_DETAILS = FLIGHT_ID + "/details";
    public static final String OFFERS_BUY = FLIGHT_ID + "/buy";
    public static final String OFFERS_CREATE = "/create";
    public static final String OFFERS_USER = "/user-offers";
    public static final String OFFERS_ORGANIZER = "/organizer-offers";
    public static final String OFFERS_ORGANIZER_DETAILS = OFFERS_ORGANIZER + OFFERS_DETAILS;
    //Paths
    public static final String OFFERS_DETAILS_URL = OFFERS + OFFERS_DETAILS;
    public static final String OFFERS_BUY_URL = OFFERS + FLIGHT_ID + "/buy";
    public static final String OFFERS_CREATE_URL = OFFERS + OFFERS_CREATE;
    public static final String OFFERS_USER_URL = OFFERS + OFFERS_USER;
    public static final String OFFERS_ORGANIZER_URL = OFFERS + OFFERS_ORGANIZER;
    public static final String OFFERS_ORGANIZER_DETAILS_URL = OFFERS + OFFERS_ORGANIZER_DETAILS;

    //COMMENT
    public static final String COMMENTS = "/comments";
    //Level 1
    public static final String COMMENTS_ADD = "/add";
    //Paths
    public static final String COMMENTS_ADD_URL = COMMENTS + COMMENTS_ADD;

    //COMPANY
    public static final String COMPANY = "/company";
    //Level 1
    public static final String COMPANY_CREATE = "/create";
    //Paths
    public static final String COMPANY_CREATE_URL = COMPANY+COMPANY_CREATE;
}
