package com.flynow.models;

import java.util.List;

public class Company {
    private String name;
    private Integer tin;    //TIN - english version of polish NIP
    private String address;
    private List<Comment> comments;
}
