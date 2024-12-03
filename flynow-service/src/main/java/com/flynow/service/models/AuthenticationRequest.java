package com.flynow.service.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class AuthenticationRequest {
    private String email;
    private String password;
}
