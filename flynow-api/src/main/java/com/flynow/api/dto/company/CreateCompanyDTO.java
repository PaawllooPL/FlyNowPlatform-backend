package com.flynow.api.dto.company;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class CreateCompanyDTO {
    private String name;
    private String TIN;
    private String address;
}
