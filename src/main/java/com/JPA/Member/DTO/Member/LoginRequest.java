package com.JPA.Member.DTO.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String email;
    private String token;
}
