package com.flynow.api.dto.company;

import com.flynow.domain.models.company.CreateCompany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CreateCompanyDTO {

    private String name;
    private String TIN;
    private String address;

    public CreateCompany toDomain() {
        return CreateCompany.builder()
                .name(name)
                .TIN(TIN)
                .address(address)
                .build();
    }
}
