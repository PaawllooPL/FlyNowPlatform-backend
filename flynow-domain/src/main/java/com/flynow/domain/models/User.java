package com.flynow.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
@Builder
public class User {
    private String username;
    private String email;
    private String passwordHash;
    private Date creationDate;
    private List<RoleEnum> roles;
}
