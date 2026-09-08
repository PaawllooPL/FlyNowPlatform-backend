package com.flynow.domain.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Role {
    private Integer id;
    private RoleEnum name;
}
