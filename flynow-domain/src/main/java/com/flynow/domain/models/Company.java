package com.flynow.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
@Builder
public class Company {

    private Integer id;
    private Integer userId;
    private String name;
    private String TIN;    //TIN - english version of polish NIP
    private String address;
}
