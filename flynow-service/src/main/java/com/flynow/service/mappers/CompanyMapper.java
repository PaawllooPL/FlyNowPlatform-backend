package com.flynow.service.mappers;

import com.flynow.domain.models.Company;
import com.flynow.repository.entities.CompanyEntity;

public class CompanyMapper {


    public Company toCompany(CompanyEntity entity) {
        return Company.builder()
                .name(entity.getName())
                .tin(entity.getTin())
                .address(entity.getAddress())
                .comments(entity.getComments().stream().map(CommentMapper::toCompany).toList())
                .build();
    }
}
