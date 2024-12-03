package com.flynow.api.controllers;

import com.flynow.api.dto.company.CreateCompanyDTO;
import com.flynow.domain.interfaces.usecases.CompanyUseCases;
import com.flynow.domain.models.company.CreateCompany;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.flynow.api.ApiPathSegments.*;

@RequiredArgsConstructor
@RestController
@RequestMapping( BASE_PATH + COMPANY)
public class CompanyController {

    private final CompanyUseCases companyUseCases;
    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @PostMapping(value = COMPANY_CREATE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createCompany(@ModelAttribute final CreateCompanyDTO createCompanyDTO) {
        CreateCompany createCompany = createCompanyDTO.toDomain();
        companyUseCases.createCompany(createCompany);
        return ResponseEntity.status(HttpStatus.CREATED).body("Stworzono firme (konto organizatora)");
    }
}
