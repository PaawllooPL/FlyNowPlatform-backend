package com.flynow.api.dto.company;

import lombok.Getter;

@Getter
public class CreateCompanyDTO {
    private String userEmail;
    private String name;
    private String TIN;
    private String address;
}
