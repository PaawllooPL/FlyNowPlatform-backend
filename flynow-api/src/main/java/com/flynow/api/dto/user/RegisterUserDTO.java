package com.flynow.api.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDTO {

    private String username;
    private String email;
    private String password;
}
