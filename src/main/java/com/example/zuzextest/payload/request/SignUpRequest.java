package com.example.zuzextest.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignUpRequest {
    private String name;
    private Set<String> role;
    private String password;
}
