package com.flynow.domain.models.company;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateCompany {

    private String name;
    private String TIN;
    private String address;
}
