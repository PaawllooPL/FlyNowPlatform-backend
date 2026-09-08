package com.flynow.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
@Builder
public class User {
    private Integer id;
    private String username;
    private String email;
    private String passwordHash;
    private List<RoleEnum> roles;
}
